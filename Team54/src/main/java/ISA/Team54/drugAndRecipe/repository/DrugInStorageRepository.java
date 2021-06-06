package ISA.Team54.drugAndRecipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.drugAndRecipe.model.DrugInStorage;
import ISA.Team54.users.model.Supplier;

public interface DrugInStorageRepository extends JpaRepository<DrugInStorage, Long>{
	List<DrugInStorage> findAllBySupplier(Supplier supplier);

}
