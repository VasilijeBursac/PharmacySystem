package ISA.Team54.drugAndRecipe.service.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ISA.Team54.drugAndRecipe.dto.DrugForERecipeDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.dto.IsAvalableDrugDTO;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.users.model.Patient;

public interface DrugService {
	public List<Drug> getDrugsForPatient(Long id);
	boolean isDrugAvailable(Long drugId, Examination examination);
	IsAvalableDrugDTO findOrFindSubstitute(long drugId,long examinationId);
	void reduceDrugQuantityInPharmacy(long drugId, int pharmacyId,int quantity) throws Exception;
	DrugSpecification getSpecificationForDrug(Long drugId);
	List<Drug> getAllDrugs();
	Drug findById(long id);
	Drug addDrug(Drug drug);
	public boolean isPatientAlergicOnDrug(Long patientId, Long drugId);
	public List<Drug> getSubstituteForDrug(Long drugId);
	public List<Drug> getSubstituteDrugsForNewDrug(List<Long> substituteDrugsIds);
	public float getDrugPriceWithDiscount(DrugInPharmacy drugInPharmacy);
	public List<DrugForERecipeDTO> getDrugsFromErecipe(Long eRecipeId); 
	public Drug getDrugByName(String name);
	
	
}
