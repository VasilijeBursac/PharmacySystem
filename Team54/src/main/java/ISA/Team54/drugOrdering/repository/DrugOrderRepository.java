package ISA.Team54.drugOrdering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ISA.Team54.drugOrdering.model.DrugsOrder;

public interface DrugOrderRepository extends JpaRepository<DrugsOrder, Long>{
	@Query("SELECT do FROM DrugsOrder do WHERE do.status = 'Waiting'")
	List<DrugsOrder> findAllWaitingOrders();
	
	@Query("SELECT do FROM DrugsOrder do WHERE do.administrator.pharmacy.id = ?1")
	List<DrugsOrder> getAllDrugOrdersForPharmacy(long pharmacyId);
	
	DrugsOrder findOneById(long drugOrderId);
	
	@Modifying
	@Query("DELETE FROM DrugsOrder do WHERE do.id = ?1")
	void deleteById(long drugOrderId);
}
