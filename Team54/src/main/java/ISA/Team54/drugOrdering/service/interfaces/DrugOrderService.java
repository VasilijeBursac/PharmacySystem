package ISA.Team54.drugOrdering.service.interfaces;

import java.util.List;

import ISA.Team54.drugOrdering.model.DrugsOrder;

public interface DrugOrderService {
	List<DrugsOrder> findAllWaitingOrders();
	DrugsOrder findById(long id);
}
