package ISA.Team54.drugAndRecipe.service.interfaces;

import ISA.Team54.drugAndRecipe.model.DrugSpecification;

public interface DrugSpecificationService {
	DrugSpecification addDrugSpecification(DrugSpecification drugSpecification);
	DrugSpecification findOneById(int id);
}
