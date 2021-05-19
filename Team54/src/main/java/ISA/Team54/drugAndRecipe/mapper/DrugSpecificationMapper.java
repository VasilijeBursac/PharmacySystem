package ISA.Team54.drugAndRecipe.mapper;

import java.util.ArrayList;
import java.util.List;

import ISA.Team54.drugAndRecipe.dto.ContraindicationDTO;
import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugSpecificationDTO;
import ISA.Team54.drugAndRecipe.dto.IngredientDTO;
import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.model.Ingredient;

public class DrugSpecificationMapper {

	public DrugSpecificationDTO DrugSpecificationIntoDrugSpecificationDTO(DrugSpecification drugSpecification,List<Drug> drugSubstitute) {
		List<DrugDTO> substituteDrugsDTO = new ArrayList<DrugDTO>();
		for(Drug d :drugSubstitute ) {
			substituteDrugsDTO.add(new DrugMapper().DrugIntoDrugDTO(d));
		}

		List<ContraindicationDTO> contraindicationDTOs = new ArrayList<ContraindicationDTO>();
		drugSpecification.getContraindications().forEach(contraindication -> contraindicationDTOs.add(ContraindicationMapper.ContraindicationToContraindicationDTO(contraindication)));
		
		List<IngredientDTO> ingredientsDTOs = new ArrayList<IngredientDTO>();
		drugSpecification.getIngredients().forEach(ingredient -> ingredientsDTOs.add(IngredientMapper.IngredientTOIngredientDTO(ingredient)));
				
		return new DrugSpecificationDTO(contraindicationDTOs,ingredientsDTOs,drugSpecification.getSuggestedDose(),substituteDrugsDTO);
	}
	
	public static DrugSpecification DrugSpecificationDTOIntoDrugSpecification(DrugSpecificationDTO drugSpecificationDTO) {
		
		List<Contraindication> contraindications = new ArrayList<Contraindication>();
		drugSpecificationDTO.getContraindications().forEach(contraindicationDTO -> contraindications.add(ContraindicationMapper.ContraindicationDTOToContraindication(contraindicationDTO)));
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		drugSpecificationDTO.getIngredients().forEach(ingredientDTO -> ingredients.add(IngredientMapper.IngredientDTOTOIngredient(ingredientDTO)));
		
		return new DrugSpecification(contraindications,ingredients,drugSpecificationDTO.getSuggestedDose());
	}
}
