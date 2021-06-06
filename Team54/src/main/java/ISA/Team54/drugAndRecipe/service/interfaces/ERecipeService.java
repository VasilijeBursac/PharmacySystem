package ISA.Team54.drugAndRecipe.service.interfaces;
import java.util.List;
import ISA.Team54.drugAndRecipe.model.ERecipe;

public interface ERecipeService {

	List<ERecipe> getAllERecipesForPatient();
	ERecipe addERecipe(ERecipe eRecipe);
}
