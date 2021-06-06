package ISA.Team54.drugOrdering.service.interfaces;

import java.util.List;

import ISA.Team54.drugOrdering.model.DrugInOrder;

public interface DrugInOrderService {

	List<DrugInOrder> findAllDrugInOrdersByOrderId(long orderId);
}
