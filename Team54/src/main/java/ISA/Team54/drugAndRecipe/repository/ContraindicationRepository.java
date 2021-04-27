package ISA.Team54.drugAndRecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;

public interface ContraindicationRepository extends JpaRepository<Contraindication, Long>{
	
}
