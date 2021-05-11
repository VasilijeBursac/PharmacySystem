package ISA.Team54.drugOrdering.service.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.repository.DrugInOrderRepository;
import ISA.Team54.drugOrdering.service.interfaces.DrugInOrderService;

@Service
public class DrugInOrderServiseImpl implements DrugInOrderService {

	@Autowired
	DrugInOrderRepository drugInOrderRepository;
	
	@Override
	public List<DrugInOrder> findAllDrugInOrdersByOrderId(long orderId) {
		return drugInOrderRepository.findAllByIdOrderId(orderId);
	}

}
