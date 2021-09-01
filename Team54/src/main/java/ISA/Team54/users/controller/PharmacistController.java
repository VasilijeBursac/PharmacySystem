package ISA.Team54.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.exceptions.DrugReservedInFutureException;
import ISA.Team54.users.dto.PharmacistInfoDTO;
import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.exceptions.EmployeeHasScheduledExaminationsException;
import ISA.Team54.users.mapper.PharmacistMapper;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.service.interfaces.PharmacistService;

@RestController
@RequestMapping(value = "/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistController {
	@Autowired
	private PharmacistService pharmacistService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('PHARMACIST')")
	public Pharmacist loadById(@PathVariable long id){
		return this.pharmacistService.findOneById(id);
	}
	
	@GetMapping("")
	public ResponseEntity<List<PharmacistInfoDTO>> getAllPharmacists(){
		try {
			List<PharmacistInfoDTO> pharmacistsDTOs = new ArrayList<PharmacistInfoDTO>();

			pharmacistService.getAllPharmacists().forEach(pharmacistInPharmacy -> 
			pharmacistsDTOs.add(PharmacistMapper.PharmacistToPharmacistInfoDTO(pharmacistInPharmacy)));
			return new ResponseEntity<>(pharmacistsDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	public ResponseEntity<List<PharmacistInfoDTO>> getAllPharmacistsInPharmacy(@PathVariable Long pharmacyId){
		try {
			List<PharmacistInfoDTO> pharmacistsInPharmacyDTOs = new ArrayList<PharmacistInfoDTO>();
			
			pharmacistService.getAllPharmacistsInPharmacy(pharmacyId).forEach(pharmacistInPharmacy -> 
			pharmacistsInPharmacyDTOs.add(PharmacistMapper.PharmacistToPharmacistInfoDTO(pharmacistInPharmacy)));
			return new ResponseEntity<>(pharmacistsInPharmacyDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/removeFromPharmacy/{pharmacistId}")
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<String> removePharmacistFromPharmacy(@PathVariable long pharmacistId){
		try {
			pharmacistService.removePharmacistFromPharmacy(pharmacistId);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (EmployeeHasScheduledExaminationsException e) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
	    	return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
}
