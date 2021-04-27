package ISA.Team54.drugAndRecipe.mapper;

import ISA.Team54.drugAndRecipe.dto.IngredientDTO;
import ISA.Team54.drugAndRecipe.model.Ingredient;

public class IngredientMapper {
	
	public static Ingredient IngredientDTOTOIngredient(IngredientDTO ingredientDTO) {
		Ingredient ingredient = new Ingredient(ingredientDTO.getName());
		ingredient.setId(ingredientDTO.getId());
		return ingredient;
	}
	
	public static IngredientDTO IngredientTOIngredientDTO(Ingredient ingredient) {
		IngredientDTO ingredientDTO = new IngredientDTO(ingredient.getIngredient());
		ingredientDTO.setId(ingredient.getId());
		return ingredientDTO;
	}
	
}
