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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.users.dto.PatientDTO;
import ISA.Team54.users.dto.PharmacyAdministratorDTO;
import ISA.Team54.users.mapper.PatientMapper;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.PharmacyAdministrator;
import ISA.Team54.users.model.User;
import ISA.Team54.users.service.interfaces.PatientService;
import ISA.Team54.users.service.interfaces.PharmacyAdministratorService;


import ISA.Team54.users.dto.UserInfoDTO;
import ISA.Team54.users.mappers.PharmacyAdministratorMapper;
import ISA.Team54.users.mappers.UserInfoMapper;


@RestController
@RequestMapping(value = "/pharmacyAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyAdministratorController {
	@Autowired
	private PharmacyAdministratorService pharmacyAdministratorService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public PharmacyAdministratorDTO loadById(@PathVariable long id){
		System.out.println(id);
		//return this.pharmacyAdministratorService.findById(id);
		PharmacyAdministrator pharmacyAdmin = this.pharmacyAdministratorService.findById(id);
		return PharmacyAdministratorMapper.pharmacyAdministratorToPharmacyAdministratorDTO(pharmacyAdmin);
	}
	
	@GetMapping("/allPharmacyAdmins")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<List<UserInfoDTO>> findAll(){
		try {
			List<UserInfoDTO> userDTOs = new ArrayList<UserInfoDTO>();
			this.pharmacyAdministratorService.findAll().forEach(pharmacyAdministrator -> userDTOs.add(UserInfoMapper.UserTOUserInfoDTO(pharmacyAdministrator)));
			return new ResponseEntity<>(userDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}

