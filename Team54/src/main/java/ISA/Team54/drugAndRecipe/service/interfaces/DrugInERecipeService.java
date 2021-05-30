package ISA.Team54.drugAndRecipe.service.interfaces;

import java.util.List;

import ISA.Team54.drugAndRecipe.model.DrugInERecipe;

public interface DrugInERecipeService {
	List<DrugInERecipe> getAllDrugInERecipeByERecipe(long eRecipeId);
	DrugInERecipe addDrugInERecipe(DrugInERecipe drugInErecipe);
}
