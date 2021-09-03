package ISA.Team54.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ISA.Team54.promotion.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>{
	List<Promotion> findAllByPharmacyId(long pharmacyId);
	
	@Modifying
	@Query("delete from Promotion p where p.id = ?1")
	void deleteById(long pharmacyId);
}
