package ISA.Team54.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.promotion.dto.PromotionDTO;
import ISA.Team54.promotion.mappers.PromotionMapper;
import ISA.Team54.promotion.model.Promotion;
import ISA.Team54.promotion.service.interfaces.PromotionService;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RestController
@RequestMapping(value = "/promotion", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromotionController {
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<List<Promotion>> getAllPromotionsForPharmacy(@PathVariable long pharmacyId) {
		try {
			return new ResponseEntity<>(promotionService.getAllPromotionsForPharmacy(pharmacyId),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 		
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public  ResponseEntity<String> addPromotion(@RequestBody PromotionDTO promotionDTO){
		try {
			Pharmacy pharmacy = pharmacyService.getPharmacyById(promotionDTO.getPharmacyId());
			promotionService.addNewPromotion(PromotionMapper.PromotionDTOAndPharmacyToPromotion(promotionDTO, pharmacy));
			return new ResponseEntity<>( HttpStatus.CREATED);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	} 
	
	@DeleteMapping("/{promotionId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<String> deletePromotion(@PathVariable long promotionId){
		try {
			promotionService.deletePromotionById(promotionId);
			return new ResponseEntity<>( HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	} 
}
