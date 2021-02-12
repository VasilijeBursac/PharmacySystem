package ISA.Team54.drugAndRecipe.service.interfaces;

import java.util.List;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;

public interface DrugInPharmacyService {
	List<DrugInPharmacy> getDrugsInPharmaciesByDrug(long id);
	List<DrugInPharmacyDTO> getAllDrugsInPharmacy(long pharmacyId);
}
