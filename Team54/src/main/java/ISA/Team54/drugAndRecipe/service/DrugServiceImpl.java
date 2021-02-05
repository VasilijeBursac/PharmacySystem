package ISA.Team54.drugAndRecipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.drugAndRecipe.dto.IsAvalableDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugAllergy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.repository.DrugAllergyRepository;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.users.model.Patient;
@Service
public class DrugServiceImpl implements DrugService {

	@Autowired
	private DrugRepository drugRepsoitory;
	@Autowired
	private ExaminationRepository examinationRepository;
	@Autowired
	private DrugAllergyRepository drugAllergyRepsoitory;
	@Autowired
	private DrugsInPharmacyRepository drugsInPharmacyRepository;
	
	@Override
	public List<Drug> getDrugsForPatient(Long id) {
		List<Drug> drugsForPatient = drugRepsoitory.findAll();
		if(drugsForPatient== null) 
			return new ArrayList<Drug>();
		
		if(drugAllergyRepsoitory.getByPatientId(id)== null)
			return drugsForPatient;
		
		for(DrugAllergy drugAllergy : drugAllergyRepsoitory.getByPatientId(id)) {
			for(Drug drug : drugRepsoitory.findAll()) {
				if(drug.getId() == drugAllergy.getPatient().getId()) {
					drugsForPatient.remove(drug);
				}
			}
			
		}
		return drugsForPatient;
	}
	
	private boolean isPatientAlergicOnDrug(Long patientId, Long drugId) {
		Drug drug = drugRepsoitory.findOneById(drugId);
		for(DrugAllergy allergyDrug : drugAllergyRepsoitory.getByPatientId(patientId)) {
			if(drug.getId()==allergyDrug.getDrug().getId()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isDrugAvailable(Long drugId, Examination examination) {
		
		DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findOneByDrugInPharmacyId(new DrugInPharmacyId(examination.getPharmacy().getId(),drugId));
		if(drugInPharmacy != null && drugInPharmacy.getQuantity()>0) {
			drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - 1);
			return true;
		}
		return false;
	}
	
	public IsAvalableDrugDTO findOrFindSubstitute(int drugId, int examinationId) {
		Examination examination = examinationRepository.findById(examinationId);
		Patient patient = examination.getPatient();
		IsAvalableDrugDTO availableDTO = new IsAvalableDrugDTO();
		boolean isAvailable = isDrugAvailable((long)drugId,examination);
		if(isAvailable) {
			availableDTO.setAvailable(true);
			return availableDTO;
		}
		List<Drug> substituteDrugsForPatient = new ArrayList<Drug>();
		Drug mainDrug = drugRepsoitory.findOneById((long)drugId);
		List<Drug> substituteDrugs = mainDrug.getMainDrugs();
		for(Drug substituteDrug : substituteDrugs) {
			if(isDrugAvailable((long)substituteDrug.getId(),examination) && isPatientAlergicOnDrug(patient.getId(),(long)substituteDrug.getId())) {
				substituteDrugsForPatient.add(substituteDrug);
			}
		}		
		availableDTO.setAvailable(false);
		for(Drug drug : substituteDrugsForPatient) {
			availableDTO.getDrugsDTO().add(new DrugMapper().DrugIntoDrugDTO(drug));
		}
		return availableDTO;
	}

	public void reduceDrugQuantityInPharmacy(long drugId, int pharmacyId,int quantity) {
		DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findOneByDrugInPharmacyId(new DrugInPharmacyId(pharmacyId,drugId));
		drugInPharmacy.setQuantity(drugInPharmacy.getQuantity()-quantity);
		drugsInPharmacyRepository.save(drugInPharmacy);
	}

	@Override
	public DrugSpecification getSpecificationForDrug(Long drugId) {
		Drug drug = drugRepsoitory.findOneById(drugId);
		if(drug!=null) {
			return drug.getDrugSpecification();
		}
		return null;
	}

	
}