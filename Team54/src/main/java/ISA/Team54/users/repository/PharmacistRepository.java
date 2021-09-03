package ISA.Team54.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ISA.Team54.users.model.Pharmacist;


public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

	Pharmacist findOneById(long id);
	List<Pharmacist> findAllByPharmacyId(long pharmacyId);
	
	@Query("select p from Pharmacist p where p.pharmacy != null")
	List<Pharmacist> findAll();
}
