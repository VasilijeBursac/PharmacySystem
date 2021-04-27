package ISA.Team54.drugAndRecipe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.repository.ContraindicationRepository;
import ISA.Team54.drugAndRecipe.repository.IngredientRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.ContraindicationService;

@Service
public class ContraindicationServiceImpl implements ContraindicationService{

	@Autowired
	private ContraindicationRepository contraIndicationRepository;
	
	@Override
	public Contraindication addContraindication(Contraindication contraindication) {
		return contraIndicationRepository.save(contraindication);
	}

}
