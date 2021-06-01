package ISA.Team54.drugAndRecipe.repository;


import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;

@Repository
public interface DrugsInPharmacyRepository extends JpaRepository<DrugInPharmacy, DrugInPharmacyId>{
	
	DrugInPharmacy findOneByDrugInPharmacyId(DrugInPharmacyId id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from DrugInPharmacy d where drug_id = ?1 and pharmaci_id = ?2")
	 @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	DrugInPharmacy findDrugInPharmacyById(long drug_id, long pharmacy_id);
    
	@Query("select d from DrugInPharmacy d where drug_id = ?1")
	List<DrugInPharmacy> findByDrugId(long id);
	
	@Query("select d from DrugInPharmacy d where drug_id = ?1 and pharmaci_id = ?2")
	DrugInPharmacy findByDrugIdAndPharmacyId(long drugId, long pharmacyId);
	
	List<DrugInPharmacy> findAllByDrugInPharmacyIdPharmaciId(long pharmacyId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "1000")})
	@Query("select d from DrugInPharmacy d where pharmaci_id = ?1")
	List<DrugInPharmacy> findAllByPharmacyId(long pharmacyId);
}
