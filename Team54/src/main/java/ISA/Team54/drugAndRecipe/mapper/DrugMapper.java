package ISA.Team54.drugAndRecipe.mapper;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.model.Drug;

public class DrugMapper {

	public DrugDTO DrugIntoDrugDTO(Drug drug)
	{
		return new DrugDTO(drug.getId(),drug.getCode(),drug.getLoyalityPoints(),drug.getName());
	}
	
	public static Drug DrugDTOIntoDrug(DrugDTO drugdto)
	{
		return new Drug(drugdto.getName(), drugdto.getCode(), drugdto.getType(), drugdto.getShape(), drugdto.getManifacturer(), drugdto.getAdditionalInfo(), drugdto.getLoyalityPoints(), drugdto.getSubstituteDrugs());
	}
	
}
