package ISA.Team54.drugOrdering.service.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugOrdering.dto.DrugInOrderRequestDTO;
import ISA.Team54.drugOrdering.dto.DrugOrderRequestDTO;
import ISA.Team54.drugOrdering.enums.OrderStatus;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugsOrder;
import ISA.Team54.drugOrdering.repository.DrugInOrderRepository;
import ISA.Team54.drugOrdering.repository.DrugOrderRepository;
import ISA.Team54.drugOrdering.service.interfaces.DrugOrderService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.PharmacyAdministrator;
import ISA.Team54.users.repository.PharmacyAdministratorRepository;

@Service
public class DrugOrderServiceImpl implements DrugOrderService{

	@Autowired
	private DrugOrderRepository drugOrderRepository;
	
	@Autowired
	private DrugInOrderRepository drugInOrderRepository;
	
	@Autowired
	private PharmacyAdministratorRepository pharmacyAdminRepository;
	
	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	
	
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

	@Override
	public List<DrugsOrder> getAllOrdersForPharmacy(long pharmacyId) {
		return drugOrderRepository.getAllDrugOrdersForPharmacy(pharmacyId);
	}

	@Override
	public void addNewDrugOrder(Date deadline, List<DrugInOrder> drugsInOrder) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PharmacyAdministrator pharmacyAdministrator = pharmacyAdminRepository.findOneById(((PharmacyAdministrator) authentication.getPrincipal()).getId());
		
		DrugsOrder drugOrder = new DrugsOrder(deadline, OrderStatus.Waiting, pharmacyAdministrator);
		drugOrder = drugOrderRepository.save(drugOrder);
		
		for(DrugInOrder drugInOrder : drugsInOrder) {
			drugInOrder.getId().setOrderId(drugOrder.getId());
			drugInPharmacyService.addDrugToPharmacy(new DrugInPharmacy(new DrugInPharmacyId(pharmacyAdministrator.getPharmacy().getId(), drugInOrder.getId().getDrugId()),
					drugInOrder.getQuantity()), true);
		}
		
		drugInOrderRepository.saveAll(drugsInOrder);
	}

}
