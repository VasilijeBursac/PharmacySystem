package ISA.Team54.drugAndRecipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.drugAndRecipe.model.ERecipe;
import ISA.Team54.users.model.Patient;

public interface ERecipeRepository extends JpaRepository<ERecipe, Long>{

	List<ERecipe> findAllByPatient(Patient patient);
}
