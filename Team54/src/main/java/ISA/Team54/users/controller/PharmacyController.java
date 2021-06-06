package ISA.Team54.users.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ISA.Team54.drugAndRecipe.dto.PharmacyForDrugDTO;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.Examination.dto.EmployeeExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationSearchDTO;
import ISA.Team54.Examination.dto.ExaminationTypeDTO;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.service.interfaces.ExaminationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.NotFoundException;

import ISA.Team54.users.dto.DrugNamesAndQuantitiesDTO;
import ISA.Team54.users.dto.PharmacyDTO;
import ISA.Team54.users.dto.PharmacyExaminationDTO;
import ISA.Team54.users.dto.PharmacyForERecipeDTO;
import ISA.Team54.users.exceptions.DrugNotFoundException;
import ISA.Team54.users.mappers.PharmacyForErecipeMapper;
import ISA.Team54.users.mappers.PharmacyMapper;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RestController
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private ExaminationService examinationService;

	@Autowired
	private DrugReservationService drugReservationService;
	
	@Autowired
	private DrugService drugService;
	
	@PostMapping("/addPharmacy")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<Pharmacy>  addPharmacy(@RequestBody PharmacyDTO pharmacyDTO){
		try {
			return new ResponseEntity<>(pharmacyService.addPharmacy(pharmacyDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/allPharmacies")
	public ResponseEntity<List<PharmacyDTO>> findAll(){
		try {
			return new ResponseEntity<List<PharmacyDTO>>(pharmacyService.findAll().stream().map(p -> PharmacyMapper.PharmacyToPharmacyDTO(p)).collect(Collectors.toList()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public PharmacyDTO loadById(@PathVariable long id){
		Pharmacy pharmacy =  this.pharmacyService.getPharmacyById(id);
		return PharmacyMapper.PharmacyToPharmacyDTO(pharmacy);
	}
	
	@PutMapping("")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN','SYSTEM_ADMIN')")
	public void updatePharmacyInfo(@RequestBody PharmacyDTO pharmacy){
		this.pharmacyService.updatePharmacyInfo(pharmacy);
	} 
	
	@PostMapping("/all-examinations")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public  ResponseEntity<List<EmployeeExaminationDTO>> getFreeDermatologistExaminationsForPharmacy(@PathVariable long id){
		return new ResponseEntity<List<EmployeeExaminationDTO>>(examinationService.getAllExaminationsForPharmacy(id, ExaminationType.DermatologistExamination), HttpStatus.OK);	
	}

	public  ResponseEntity<List<EmployeeExaminationDTO>> getAllExaminationsForPharmacy(@RequestBody PharmacyExaminationDTO examinationSearch){
		List<EmployeeExaminationDTO> examinations = examinationService.getAllExaminationsForPharmacy(examinationSearch.getId(), examinationSearch.getType());
		return new ResponseEntity<List<EmployeeExaminationDTO>>(examinations, HttpStatus.OK);	
	}
	
	@PostMapping("/examinations")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public  ResponseEntity<List<EmployeeExaminationDTO>> getExaminationsForPharmacyAndDate(@RequestBody PharmacyExaminationDTO examinationSearch){
		List<EmployeeExaminationDTO> examinations = examinationService.getExaminationsForPharmacyAndDate(examinationSearch.getId(), examinationSearch.getType(), examinationSearch.getDate());
		return new ResponseEntity<List<EmployeeExaminationDTO>>(examinations, HttpStatus.OK);	
	}
	
	@PostMapping("/search-examinations")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<PharmacyDTO>> getFreePharmaciesForInterval(@RequestBody ExaminationSearchDTO examinationSearchDTO){
		List<Pharmacy> pharmacies=  examinationService.getFreePharmaciesForInterval(examinationSearchDTO.getDate(), examinationSearchDTO.getType());
		List<PharmacyDTO> pharmacyDTOs = new ArrayList<PharmacyDTO>();
		
		pharmacies.forEach(p -> {
			p.setPharmacistPrice(pharmacyService.getPharmacistPriceWithDiscount(p.getPharmacistPrice()));
			pharmacyDTOs.add(PharmacyMapper.PharmacyToPharmacyDTO(p));
			});
		return new ResponseEntity<List<PharmacyDTO>>(pharmacyDTOs, HttpStatus.OK);
	}

	@GetMapping("/patient-pharmacies")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<PharmacyDTO>> getPatientPharmacies(){
		try {
			List<Pharmacy> pharmacies = pharmacyService.getPatientPharmacies();
			pharmacies.addAll(drugReservationService.getPatientPharmacies());
			List<Pharmacy> distinctPharmacies = getPharmaciesWithoutDuplicates(pharmacies);
			List<PharmacyDTO> pharmacyDTOs = new ArrayList<PharmacyDTO>();
			distinctPharmacies.forEach(
					p -> pharmacyDTOs.add(PharmacyMapper.PharmacyToPharmacyDTO(p))
			);
			return new ResponseEntity<List<PharmacyDTO>>(pharmacyDTOs, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<Pharmacy> getPharmaciesWithoutDuplicates(List<Pharmacy> pharmacies) {
		List<Pharmacy> distinctPharmacies = new ArrayList<Pharmacy>();
		pharmacies.forEach(
				p -> {
					if(CheckIfPharmacyUnique(p, distinctPharmacies))
						distinctPharmacies.add(p);
				}
		);

		return distinctPharmacies;
	}

	@PostMapping("{id}/dermatologist-examinations")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public  ResponseEntity<List<EmployeeExaminationDTO>> getFreeDermatologistExaminationsForPharmacy(@PathVariable long id, @RequestBody ExaminationTypeDTO type){
		return new ResponseEntity<List<EmployeeExaminationDTO>>(examinationService.getExaminationsForPharmacy(id, type.getType()), HttpStatus.OK);
	}

	private boolean CheckIfPharmacyUnique(Pharmacy pharmacy, List<Pharmacy> pharmacies) {
		for (Pharmacy p : pharmacies) {
			if(p.getId() == pharmacy.getId())
				return false;
		}
		return true;
	}
	
	@PostMapping("/pharmaciesForErecipe")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<PharmacyForERecipeDTO>> getPharmaciesForErecipe(@RequestBody DrugNamesAndQuantitiesDTO drugNamesAndQuantitiesDTO){
		try {
			List<PharmacyForERecipeDTO> pharmacyForERecipeDTOs = new ArrayList<>();
			for(Map.Entry pharmacy : pharmacyService.getPharmaciesWithTotalPrices(
												drugNamesToDrugIds(drugNamesAndQuantitiesDTO.getDrugNames()),
												drugNamesAndQuantitiesDTO.getDrugQuantities()).entrySet()){    
				pharmacyForERecipeDTOs.add(PharmacyForErecipeMapper.PharmacyToPharmacyForErecipeDTO(
						pharmacyService.getPharmacyById(Long.parseLong(pharmacy.getKey().toString())),
						Float.parseFloat(pharmacy.getValue().toString())));
			}
			 return new ResponseEntity<>(pharmacyForERecipeDTOs, HttpStatus.OK);
		} catch (DrugNotFoundException e){
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e){
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private List<Long> drugNamesToDrugIds(List<String> drugNames) throws DrugNotFoundException{
		List<Long> drugIds = new ArrayList();
		for(int i = 0; i < drugNames.size(); i++) {
			if(drugService.getDrugByName(drugNames.get(i)) == null)
				throw new DrugNotFoundException();
			drugIds.add(drugService.getDrugByName(drugNames.get(i)).getId());
		}
		return drugIds;
	}
}
