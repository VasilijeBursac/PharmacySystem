package ISA.Team54.users.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyRequestDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.users.dto.DermatologistInfoDTO;
import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.dto.DermatologistToPharmacyDTO;
import ISA.Team54.users.dto.PharmacistInfoDTO;
import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.dto.UserInfoDTO;
import ISA.Team54.users.exceptions.EmployeeHasScheduledExaminationsException;
import ISA.Team54.users.mapper.DermatologistMapper;
import ISA.Team54.users.mapper.PharmacistMapper;
import ISA.Team54.users.mappers.UserInfoMapper;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.service.interfaces.DermatologistService;

@RestController
@RequestMapping(value = "/dermatologist", produces = MediaType.APPLICATION_JSON_VALUE)
public class DermatologistController {
	@Autowired
	private DermatologistService dermatologistService;
	
	@GetMapping("/allDermatologists")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<List<UserInfoDTO>> findAll(){
		try {
			List<UserInfoDTO> userDTOs = new ArrayList<UserInfoDTO>();
			this.dermatologistService.findAll().forEach(dermatologist -> userDTOs.add(UserInfoMapper.UserTOUserInfoDTO(dermatologist)));
			return new ResponseEntity<>(userDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('DERMATOLOGIST')")
	public Dermatologist loadById(@PathVariable long id){
		return this.dermatologistService.findOneById(id);
	}
	
	@GetMapping("")
	public ResponseEntity<List<DermatologistInfoDTO>> getAllDermatologists(){
		try {
			List<DermatologistInfoDTO> dermatologistsDTOs = new ArrayList<DermatologistInfoDTO>();

			dermatologistService.getAllDermatologists().forEach(dermatologist -> 
			dermatologistsDTOs.add(DermatologistMapper.DermatologistToDermatologistInfoDTO(dermatologist)));
			return new ResponseEntity<>(dermatologistsDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	public List<DermatologistInfoDTO> getAllDermatologistsInPharmacy(@PathVariable Long pharmacyId){
		List<DermatologistInfoDTO> dermatologistsInPharmacyDTOs = new ArrayList<DermatologistInfoDTO>();
		
		dermatologistService.getAllDermatologistsInPharmacy(pharmacyId).forEach(dermatologistInPharmacy -> 
		dermatologistsInPharmacyDTOs.add(DermatologistMapper.DermatologistToDermatologistInfoDTO(dermatologistInPharmacy)));
		
		return dermatologistsInPharmacyDTOs;
	}
	
	@GetMapping("/notInPharmacy/{pharmacyId}")
	public List<DermatologistInfoDTO> getAllDermatologistsNotInPharmacy(@PathVariable Long pharmacyId){
		List<DermatologistInfoDTO> dermatologistsNotInPharmacyDTOs = new ArrayList<DermatologistInfoDTO>();
		
		dermatologistService.getAllDermatologistsNotInPharmacy(pharmacyId).forEach(dermatologistNotInPharmacy -> 
		dermatologistsNotInPharmacyDTOs.add(DermatologistMapper.DermatologistToDermatologistInfoDTO(dermatologistNotInPharmacy)));
		
		return dermatologistsNotInPharmacyDTOs;
	}
	
	@DeleteMapping("/removeFromPharmacy/{dermatologistId}/{pharmacyId}")
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<String> removeDermatologistFromPharmacy(@PathVariable long dermatologistId, @PathVariable long pharmacyId){
		try {
			dermatologistService.removeDermatologistFromPharmacy(dermatologistId, pharmacyId);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (EmployeeHasScheduledExaminationsException e) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
	    	return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addToPharmacy/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public  ResponseEntity<String> addDermatologistToPharmacy(@RequestBody DermatologistToPharmacyDTO dermatologistToPharmacyDTO, @PathVariable long pharmacyId){
		try {
			dermatologistService.addDermatologistToPharmacy(dermatologistToPharmacyDTO, pharmacyId);
			return new ResponseEntity<>( HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	} 
}
