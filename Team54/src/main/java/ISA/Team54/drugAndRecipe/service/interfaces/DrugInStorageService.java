package ISA.Team54.drugAndRecipe.service.interfaces;

import java.util.List;

import ISA.Team54.drugAndRecipe.model.DrugInStorage;
import ISA.Team54.users.model.Supplier;

public interface DrugInStorageService {
	
	List<DrugInStorage> findAllDrugsInStorageForSupplier();
}
