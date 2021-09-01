package ISA.Team54.drugOrdering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ISA.Team54.drugOrdering.model.DrugInOrder;

public interface DrugInOrderRepository extends JpaRepository<DrugInOrder, Long>{
	List<DrugInOrder> findAllByIdOrderId(long orderId);
}
