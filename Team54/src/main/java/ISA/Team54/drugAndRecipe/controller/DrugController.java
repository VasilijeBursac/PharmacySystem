package ISA.Team54.drugAndRecipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.model.EachTestNotifier;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.dto.DrugSpecificationDTO;
import ISA.Team54.drugAndRecipe.dto.IsAvalableDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.mapper.DrugSpecificationMapper;
import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.repository.ContraindicationRepository;
import ISA.Team54.drugAndRecipe.repository.IngredientRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugSpecificationService;


@RestController
@RequestMapping(value = "/drugs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugController {
	
	@Autowired
	private DrugService drugService;

	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	@Autowired
	private DrugSpecificationService drugSpecificationService;
	
	@GetMapping("forPatient/{patientId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public List<DrugDTO> drugsForPatient(@PathVariable int patientId){
		List<DrugDTO> drugsForPatient = new ArrayList<DrugDTO>();
		for(Drug drug : drugService.getDrugsForPatient((long) patientId)) {
			drugsForPatient.add(new DrugMapper().DrugIntoDrugDTO(drug));
		}
		return drugsForPatient;
	}
	
	@GetMapping("/isAvailableInPharmacy/{drugId}/{examinationId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public IsAvalableDrugDTO isAvailable(@PathVariable Long drugId,@PathVariable Long examinationId){
		return drugService.findOrFindSubstitute(drugId,examinationId);
	}
	
	@GetMapping("/drugSpecification/{drugId}")
	public DrugSpecificationDTO getDrugSpecification(@PathVariable Long drugId){
		DrugSpecification drugSpecification = drugService.getSpecificationForDrug(drugId);
		List<Drug> drugSubstitute = drugService.getSubstituteForDrug(drugId);
		return new DrugSpecificationMapper().DrugSpecificationIntoDrugSpecificationDTO(drugSpecification,drugSubstitute);
	}
	
	@GetMapping("")
	public ResponseEntity<List<DrugDTO>> getAll(){
		try {
			List<DrugDTO> drugDTOs = new ArrayList<DrugDTO>();
			this.drugService.getAllDrugs().forEach(drug -> 
				drugDTOs.add(DrugMapper.DrugIntoDrugDTOForTable(drug)));
			return new ResponseEntity<>(drugDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addDrug")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<String> addDrug(@RequestBody DrugDTO drugDTO){
		try {
			Drug newDrug = DrugMapper.DrugDTOIntoDrug(drugDTO);
			newDrug.setDrugSpecification(drugSpecificationService.findOneById(drugDTO.getDrugSpecification()));
			newDrug.setSubstituteDrugs(drugService.getSubstituteDrugsForNewDrug(drugDTO.getSubstituteDrugs()));
			drugService.addDrug(newDrug);
			return new ResponseEntity<>( HttpStatus.CREATED);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
		
	} 

	@GetMapping("/byPharmacyId/{pharmacyId}")
	public ResponseEntity<List<DrugInPharmacyDTO>> getAllDrugsInPharmacy(@PathVariable long pharmacyId){
		try{
			return new ResponseEntity<>(drugInPharmacyService.getAllDrugsInPharmacy(pharmacyId),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	

}
