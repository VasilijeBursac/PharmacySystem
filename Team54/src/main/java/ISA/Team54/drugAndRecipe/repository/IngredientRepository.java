package ISA.Team54.drugAndRecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.drugAndRecipe.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	
}
