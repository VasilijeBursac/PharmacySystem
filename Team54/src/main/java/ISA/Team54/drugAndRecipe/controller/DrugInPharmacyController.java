package ISA.Team54.drugAndRecipe.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyPricelistDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyRequestDTO;
import ISA.Team54.drugAndRecipe.dto.PharmacyForDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugOrdering.dto.DrugOrderRequestDTO;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugInOrderId;
import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.exceptions.DrugReservedInFutureException;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RestController
@RequestMapping(value = "/drugInPharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugInPharmacyController {

	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping("/pharmaciesForDrug/{drugId}")
	public ResponseEntity<List<PharmacyForDrugDTO>> getAllPharmaciesForDrug(@PathVariable long drugId){
		try {
			List<PharmacyForDrugDTO> pharmacyForDrugDTOs = new ArrayList<PharmacyForDrugDTO>();
			this.drugInPharmacyService.getDrugsInPharmaciesByDrug(drugId).forEach(drugInPharmacy -> 
				pharmacyForDrugDTOs.add(DrugInPharmacyToPharmacyForDrugDTO(drugInPharmacy)));
			return new ResponseEntity<>(pharmacyForDrugDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private PharmacyForDrugDTO DrugInPharmacyToPharmacyForDrugDTO(DrugInPharmacy drugInPharmacy) {
		Pharmacy pharmacy = pharmacyService.getPharmacyById(drugInPharmacy.getDrugInPharmacyId().getPharmaciId());
		return new PharmacyForDrugDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAddress(), drugInPharmacy.getPricelist().getPrice());
	}
	
	
	@DeleteMapping("/removeFromPharmacy/{drugId}/{pharmacyId}")
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<String> removeDrugFromPharmacy(@PathVariable long drugId, @PathVariable long pharmacyId){
		try {
			drugInPharmacyService.removeDrugFromPharmacy(drugId, pharmacyId);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (DrugReservedInFutureException e) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
	    	return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	    }
		
	}
	
	@PostMapping("/addToPharmacy")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public  ResponseEntity<String> addDrugToPharmacy(@RequestBody DrugInPharmacyRequestDTO drugInPharmacyRequestDTO){
		try {
			DrugInPharmacy drugInPharmacy = DrugInPharmacyMapper.DrugInPharmacyRequestDTOToDrugInPharmacy(drugInPharmacyRequestDTO);
			drugInPharmacyService.addDrugToPharmacy(drugInPharmacy, false);
			return new ResponseEntity<>( HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	}
	
	@PutMapping("/editPrice")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public  ResponseEntity<String> editDrugInPharmacyPrice(@RequestBody DrugInPharmacyPricelistDTO drugInPharmacyPricelistDTO){
		try {
			drugInPharmacyService.editDrugInPharmacyPrice(drugInPharmacyPricelistDTO.getDrugInPharmacyId(), drugInPharmacyPricelistDTO.getPriceValidDateRange(), drugInPharmacyPricelistDTO.getPrice());
			return new ResponseEntity<>( HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	} 
}
