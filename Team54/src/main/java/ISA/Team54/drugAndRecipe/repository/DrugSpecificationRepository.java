package ISA.Team54.drugAndRecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import ISA.Team54.drugAndRecipe.model.DrugSpecification;

public interface DrugSpecificationRepository extends JpaRepository<DrugSpecification, Long>{
	DrugSpecification findById(long id);
}
