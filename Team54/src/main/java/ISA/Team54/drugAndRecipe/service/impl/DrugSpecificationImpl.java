package ISA.Team54.drugAndRecipe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ISA.Team54.drugAndRecipe.model.DrugSpecification;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugSpecificationRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugSpecificationService;
@Service
public class DrugSpecificationImpl implements DrugSpecificationService{

	@Autowired
	private DrugRepository drugRepsoitory;
	
	@Autowired
	private DrugSpecificationRepository drugSpecificationRepository;

	@Override
	public DrugSpecification addDrugSpecification(DrugSpecification drugSpecification) {
		return drugSpecificationRepository.save(drugSpecification);
	}

	@Override
	public DrugSpecification findOneById(int id) {
		return drugSpecificationRepository.findById((long)id);
	}
	

}
