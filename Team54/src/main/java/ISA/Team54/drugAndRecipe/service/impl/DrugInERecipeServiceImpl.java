package ISA.Team54.drugAndRecipe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.DrugInERecipe;
import ISA.Team54.drugAndRecipe.repository.DrugInERecipeRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInERecipeService;
@Service
public class DrugInERecipeServiceImpl implements DrugInERecipeService{

	@Autowired
	private DrugInERecipeRepository drugInERecipeRepository;
	
	@Override
	public List<DrugInERecipe> getAllDrugInERecipeByERecipe(long eRecipeId) {
		if(drugInERecipeRepository.findAllByERecipeId(eRecipeId).size() == 0)
			return null;
		return drugInERecipeRepository.findAllByERecipeId(eRecipeId);
	}

	@Override
	public DrugInERecipe addDrugInERecipe(DrugInERecipe drugInErecipe) {
		return drugInERecipeRepository.save(drugInErecipe);
	}

}
