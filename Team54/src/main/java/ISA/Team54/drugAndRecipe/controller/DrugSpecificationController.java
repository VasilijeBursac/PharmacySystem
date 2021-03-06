package ISA.Team54.drugAndRecipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugSpecificationDTO;
import ISA.Team54.drugAndRecipe.dto.PharmacyForDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugSpecificationMapper;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugSpecificationService;

@RestController
@RequestMapping(value = "/drugSpecification", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugSpecificationController {
	@Autowired
	private DrugSpecificationService drugSpecificationService;
	
	@PostMapping("/addDrugSpecification")
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public  ResponseEntity<DrugSpecification> addDrugSpecification(@RequestBody DrugSpecificationDTO drugSpecificationDTO){
		try {
			return new ResponseEntity<>(drugSpecificationService.addDrugSpecification(DrugSpecificationMapper.DrugSpecificationDTOIntoDrugSpecification(drugSpecificationDTO)), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
	} 
	
}
