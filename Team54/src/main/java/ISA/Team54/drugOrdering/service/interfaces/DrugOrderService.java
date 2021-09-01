package ISA.Team54.drugOrdering.service.interfaces;

import java.util.Date;
import java.util.List;

import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugsOrder;

public interface DrugOrderService {
	List<DrugsOrder> findAllWaitingOrders();
	DrugsOrder findById(long id);
	List<DrugsOrder> getAllOrdersForPharmacy(long pharmacyId);
	void addNewDrugOrder(Date deadline, List<DrugInOrder> drugsInOrder); //throws Exception;
	void editDrugOrder(long orderId, Date deadline, List<DrugInOrder> drugsInOrder) throws Exception;
	void deleteDrugOrder(long orderId) throws Exception;
}
