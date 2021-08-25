package ISA.Team54.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.users.dto.PharmacistInfoDTO;
import ISA.Team54.users.dto.PharmacistRequestDTO;
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
	
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	public List<PharmacistInfoDTO> getAllPharmacistsInPharmacy(@PathVariable Long pharmacyId){
		List<PharmacistInfoDTO> pharmacistsInPharmacyDTOs = new ArrayList<PharmacistInfoDTO>();
		
		pharmacistService.getAllPharmacistsInPharmacy(pharmacyId).forEach(pharmacistInPharmacy -> 
		pharmacistsInPharmacyDTOs.add(PharmacistMapper.PharmacistToPharmacistInfoDTO(pharmacistInPharmacy)));
		
		return pharmacistsInPharmacyDTOs;
	}	
}
