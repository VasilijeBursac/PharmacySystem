package ISA.Team54.drugAndRecipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ISA.Team54.drugAndRecipe.model.DrugInERecipe;
import ISA.Team54.drugAndRecipe.model.ERecipe;
import ISA.Team54.users.model.Patient;


public interface DrugInERecipeRepository extends JpaRepository<DrugInERecipe, Long>{

	@Query("SELECT d FROM DrugInERecipe d WHERE e_recipe_id = ?1")
	List<DrugInERecipe> findAllByERecipeId(long eRecipeId);
}
