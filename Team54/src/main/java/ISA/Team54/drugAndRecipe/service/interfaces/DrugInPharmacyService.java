package ISA.Team54.drugAndRecipe.service.interfaces;

import java.util.List;

import org.springframework.dao.PessimisticLockingFailureException;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.exceptions.DrugReservedInFutureException;

public interface DrugInPharmacyService {
	List<DrugInPharmacy> getDrugsInPharmaciesByDrug(long id);
	List<DrugInPharmacy> getDrugsInPharmaciesByPharmacy(long id);
	List<DrugInPharmacyDTO> getAllDrugsInPharmacy(long pharmacyId);
	List<DrugInPharmacy> getAllDrugsInPharmacy();
	void decreaseDrugQuantities(List<Long> drugIds, long pharmacyId, List<Integer> quantities) throws DrugOutOfStockException;
	void removeDrugFromPharmacy(long drugId, long pharmacyId) throws DrugReservedInFutureException;
	List<Drug> getDrugsForImportToPharmacy(long pharmacyId);
}
