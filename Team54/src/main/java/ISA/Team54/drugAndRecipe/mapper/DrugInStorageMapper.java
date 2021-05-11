package ISA.Team54.drugAndRecipe.mapper;

import ISA.Team54.drugAndRecipe.dto.DrugInStorageDTO;
import ISA.Team54.drugAndRecipe.model.DrugInStorage;

public class DrugInStorageMapper {

	public static DrugInStorageDTO DrugInStorageToDrugInStorageDTO(DrugInStorage drugInStorage) {
		return new DrugInStorageDTO(drugInStorage.getDrug().getName(),drugInStorage.getQuantity());
	}
}
