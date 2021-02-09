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

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugSpecificationDTO;
import ISA.Team54.drugAndRecipe.dto.IsAvalableDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.mapper.DrugSpecificationMapper;
import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.model.Ingredient;
import ISA.Team54.drugAndRecipe.repository.ContraindicationRepository;
import ISA.Team54.drugAndRecipe.repository.IngredientRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugSpecificationService;
import ISA.Team54.users.dto.PharmacyDTO;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RestController
@RequestMapping(value = "/drugs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugController {
	@Autowired
	private DrugService drugService;
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private ContraindicationRepository contraIndicationRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	
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
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public DrugSpecificationDTO getDrugSpecification(@PathVariable Long drugId){
		DrugSpecification drugSpecification = drugService.getSpecificationForDrug(drugId);
		return new DrugSpecificationMapper().DrugSpecificationIntoDrugSpecificationDTO(drugSpecification);
	}
	
	@GetMapping("")
	public List<Drug> getAll(){
		return drugService.getAllDrugs();
	}  
	
	/*	@PostMapping("/addDrug")
	//@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<DrugSpecification>  addDrug(){
		List<Contraindication> cList = new ArrayList<Contraindication>();
		Contraindication c = new Contraindication("Kontra indikacija2");
		contraIndicationRepository.save(c);
		cList.add(c);
		Ingredient i = new Ingredient("ingredient2");
		List<Ingredient> inList = new ArrayList<Ingredient>();
		ingredientRepository.save(i);
		inList.add(i);
		Drug d = new Drug("lek2","12e","anestetik","pilula","hemofarm","no info",35);		
		Drug d2 = new Drug("lek3","12e","anestetik","pilula","hemofarm","no info",35);
		drugService.addDrug(d);
		drugService.addDrug(d2);
		DrugSpecification ds = new DrugSpecification(cList,inList,"doza preporucena",d);
		DrugSpecification ds2 = new DrugSpecification(cList,inList,"doza preporucena",d2);
		List<Drug> sDruggs = new ArrayList<>();
		sDruggs.add(d);
		sDruggs.add(d2);
		d.setSubstituteDrugs(sDruggs);
		return new ResponseEntity<>(drugSpecificationService.addDrugSpecification(ds), HttpStatus.OK);	
	} */
	
	@PostMapping("/addDrug")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<Drug> addDrug(@RequestBody DrugDTO drugDTO){
		Drug newDrug = drugService.addDrug(DrugMapper.DrugDTOIntoDrug(drugDTO));
		newDrug.setSubstituteDrugs(drugDTO.getSubstituteDrugs());
		return new ResponseEntity<>(drugService.addDrug(DrugMapper.DrugDTOIntoDrug(drugDTO)), HttpStatus.OK);	
	} 
}
