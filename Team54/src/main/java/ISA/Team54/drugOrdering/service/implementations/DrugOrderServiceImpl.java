package ISA.Team54.drugOrdering.service.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ISA.Team54.drugOrdering.model.DrugsOrder;
import ISA.Team54.drugOrdering.repository.DrugOrderRepository;
import ISA.Team54.drugOrdering.service.interfaces.DrugOrderService;

@Service
public class DrugOrderServiceImpl implements DrugOrderService{

	@Autowired
	private DrugOrderRepository drugOrderRepository;
	
	@Override
	public List<DrugsOrder> findAllWaitingOrders() {
		if(drugOrderRepository.findAllWaitingOrders().size() != 0)
			return drugOrderRepository.findAllWaitingOrders();
		return null;
	}

	@Override
	public DrugsOrder findById(long id) {
		return drugOrderRepository.findById(id).orElse(null);
	}

}
