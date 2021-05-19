package ISA.Team54.drugAndRecipe.mapper;

import java.util.ArrayList;
import java.util.List;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.Drug;

public class DrugMapper {

	public static DrugDTO DrugIntoDrugDTO(Drug drug)
	{
		return new DrugDTO(drug.getId(),drug.getCode(),drug.getLoyalityPoints(),drug.getName());
	}
	
	public static DrugDTO DrugIntoDrugDTOForTable(Drug drug)
	{
		return new DrugDTO(drug.getId(),drug.getCode(),drug.getLoyalityPoints(),drug.getName(),
				drug.getType(),drug.getShape(),drug.getManifacturer(),drug.getAdditionalInfo(),drug.getRatings());
	}
	
	public static Drug DrugDTOIntoDrug(DrugDTO drugdto)
	{		
		return new Drug(drugdto.getName(), drugdto.getCode(), drugdto.getType(), drugdto.getShape(), 
				drugdto.getManifacturer(), drugdto.getAdditionalInfo(), drugdto.getLoyalityPoints());
	}
	
}
