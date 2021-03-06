package ISA.Team54.drugAndRecipe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ISA.Team54.drugAndRecipe.dto.DrugForERecipeDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.dto.ERecipeDTO;
import ISA.Team54.drugAndRecipe.dto.IsAvalableDrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInERecipe;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.repository.DrugAllergyRepository;
import ISA.Team54.drugAndRecipe.repository.DrugInERecipeRepository;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;

@Service
@Transactional(readOnly = false)
public class DrugServiceImpl implements DrugService {

	@Autowired
	private DrugRepository drugRepository;
	@Autowired
	private ExaminationRepository examinationRepository;
	@Autowired
	private DrugsInPharmacyRepository drugsInPharmacyRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LoyaltyRepository loyaltyRepository;
	@Autowired
	private DrugInERecipeRepository drugInERecipeRepository;
	
	@Override
	public List<Drug> getDrugsForPatient(Long id) {
		List<Drug> allDrugs = drugRepository.findAll();
		List<Drug> drugsForPatient = new ArrayList<Drug>();
		if(allDrugs== null) 
			return new ArrayList<Drug>();
		
		
		for(Drug drugAllergy : allDrugs) {
			if(isPatientAlergicOnDrug(id,drugAllergy.getId())) {
				drugsForPatient.add(drugAllergy);
			}
			
		}
		return drugsForPatient;
	}
	
	public boolean isPatientAlergicOnDrug(Long patientId, Long drugId) {
		Drug drug = drugRepository.findOneById(drugId);
		Patient patient = patientRepository.findOneById(patientId);
		for(Drug allergyDrug : patient.getDrugAllergies()) {
			if(drug.getId()==allergyDrug.getId()) {
				return false;
			}
		}
		return true;
	}

   @Transactional(readOnly = false)
	public boolean isDrugAvailable(Long drugId, Examination examination) {
		
		DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findOneByDrugInPharmacyId(new DrugInPharmacyId(examination.getPharmacy().getId(),drugId));
		if(drugInPharmacy != null && drugInPharmacy.getQuantity()>0) {
			drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - 1);
			return true;
		}
		return false;
	}
	@Transactional(readOnly = false)
	public IsAvalableDrugDTO findOrFindSubstitute(long drugId, long examinationId) {
		Examination examination = examinationRepository.findOneById(examinationId);
		Patient patient = examination.getPatient();
		IsAvalableDrugDTO availableDTO = new IsAvalableDrugDTO();
		boolean isAvailable = isDrugAvailable(drugId,examination);
		if(isAvailable) {
			availableDTO.setAvailable(true);
			return availableDTO;
		}
		Drug missingDrug = drugRepository.findOneById(drugId);
		emailService.sendEmail("team54isa@gmail.com","MANJAK LEKA","-- LEK --  ime :"+missingDrug.getName()+" code :" + missingDrug.getCode() + " nedostaje u bolnici");
		List<Drug> substituteDrugsForPatient = new ArrayList<Drug>();
		Drug mainDrug = drugRepository.findOneById((long)drugId);
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void reduceDrugQuantityInPharmacy(long drugId, int pharmacyId,int quantity) throws Exception {
		try {
		DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findDrugInPharmacyById(drugId,pharmacyId);
			drugInPharmacy.setQuantity(drugInPharmacy.getQuantity()-quantity);
				drugsInPharmacyRepository.save(drugInPharmacy);
		}catch(Exception e) {
			throw new Exception();
		}
	}

	@Override
	public DrugSpecification getSpecificationForDrug(Long drugId) {
		Drug drug = drugRepository.findOneById(drugId);
		if(drug!=null) {
			return drug.getDrugSpecification();
		}
		return null;
	}
	@Override
	public List<Drug> getAllDrugs() {
		if(drugRepository.findAll().size() != 0)
			return drugRepository.findAll();
		return null;
	}

	@Override
	public Drug findById(long id) {
		return drugRepository.findById(id).orElse(null);
	}

	@Override
	public List<Drug> getSubstituteForDrug(Long drugId) {
		Drug mainDrug = drugRepository.findOneById((long)drugId);
		return mainDrug.getSubstituteDrugs();
	}

	@Override
	public Drug addDrug(Drug drug) {
		return drugRepository.save(drug);
	}

	@Override
	public List<Drug> getSubstituteDrugsForNewDrug(List<Long> substituteDrugsIds) {
		List<Drug> substituteDrugs = new ArrayList<>();
		substituteDrugsIds.forEach(subDrugId -> substituteDrugs.add(drugRepository.findOneById(subDrugId)));
		return substituteDrugs;
	}

	@Override
	public float getDrugPriceWithDiscount(DrugInPharmacy drugInPharmacy) {		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		float price = drugInPharmacy.getPricelist().getPrice();
		return (float) (0.01 * price * (100 - loyaltyRepository.getLoyaltyCategory(patient.getLoyaltyPoints()).getDiscount())) ;
		    
	}
	
	@Override
	public List<DrugForERecipeDTO> getDrugsFromErecipe(Long eRecipeId) {
		List<Drug> allDrugs = drugRepository.findAll();
		List<DrugInERecipe> drugsInErecipe = drugInERecipeRepository.findAllByERecipeId(eRecipeId);
		List<DrugForERecipeDTO> drugsFromErecipe = new ArrayList<>();
		if(allDrugs.size() == 0 || drugsInErecipe.size() == 0) 
			return null;		
		
		for(Drug drug : allDrugs) {
			for(DrugInERecipe drugInERecipe : drugsInErecipe) {			
				if(drug.getId() == drugInERecipe.getId().getDrugId())
					drugsFromErecipe.add(new DrugForERecipeDTO(drug.getCode(),drug.getName(),drugInERecipe.getQuantityInERecipe()));
			}			
		}
		return drugsFromErecipe;
	}

	@Override
	public Drug getDrugByName(String name) {
		return drugRepository.findOneByName(name);
	}
	
}
