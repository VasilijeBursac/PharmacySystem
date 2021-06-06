package ISA.Team54.drugAndRecipe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.Ingredient;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.IngredientRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.IngredientService;
@Service
public class IngredientServiceImpl implements IngredientService{

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Override
	public Ingredient addIngredient(Ingredient ingredient) {
		return ingredientRepository.save(ingredient);
	}

}
