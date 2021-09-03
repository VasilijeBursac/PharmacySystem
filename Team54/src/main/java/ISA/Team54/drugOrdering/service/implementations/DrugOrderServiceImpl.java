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
import ISA.Team54.drugOrdering.exceptions.OrderAlreadyFulfilledException;
import ISA.Team54.drugOrdering.exceptions.OrderHasOffersException;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugsOrder;
import ISA.Team54.drugOrdering.repository.DrugInOrderRepository;
import ISA.Team54.drugOrdering.repository.DrugOrderRepository;
import ISA.Team54.drugOrdering.repository.OfferRepository;
import ISA.Team54.drugOrdering.service.interfaces.DrugOrderService;
import ISA.Team54.exceptions.DrugReservedInFutureException;
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
	private OfferRepository offerRepository;
	
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
		
		addDrugsToOrderAndPharmacy(drugsInOrder, drugOrder.getId(), pharmacyAdministrator.getPharmacy().getId());
	}

	public void deleteDrugOrder(long orderId) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PharmacyAdministrator pharmacyAdministrator = pharmacyAdminRepository.findOneById(((PharmacyAdministrator) authentication.getPrincipal()).getId());
		
		DrugsOrder drugOrder = drugOrderRepository.findOneById(orderId);
		
		if(drugOrder.getStatus() == OrderStatus.Fulfilled)
			throw new OrderAlreadyFulfilledException();
		
		if(checkIfOrderHasOffers(orderId))
			throw new OrderHasOffersException();
		
		drugOrderRepository.deleteById(orderId);
		
		List<DrugInOrder> existingDrugsInOrder = drugInOrderRepository.findAllByIdOrderId(orderId);
		removeDrugsFromOrderAndPharmacy(existingDrugsInOrder, orderId, pharmacyAdministrator.getPharmacy().getId());
	}
	
	@Override
	public void editDrugOrder(long orderId, Date deadline, List<DrugInOrder> drugsInOrder) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PharmacyAdministrator pharmacyAdministrator = pharmacyAdminRepository.findOneById(((PharmacyAdministrator) authentication.getPrincipal()).getId());
		
		DrugsOrder drugOrder = drugOrderRepository.findOneById(orderId);
		
		if(drugOrder.getStatus() == OrderStatus.Fulfilled)
			throw new OrderAlreadyFulfilledException();
		
		if(checkIfOrderHasOffers(orderId))
			throw new OrderHasOffersException();
		
		drugOrder.setDeadline(deadline);
		drugOrderRepository.save(drugOrder);
		
		List<DrugInOrder> existingDrugsInOrder = drugInOrderRepository.findAllByIdOrderId(orderId);

		removeDrugsFromOrderAndPharmacy(existingDrugsInOrder, orderId, pharmacyAdministrator.getPharmacy().getId());
		addDrugsToOrderAndPharmacy(drugsInOrder, orderId, pharmacyAdministrator.getPharmacy().getId());
	}

	public void addDrugsToOrderAndPharmacy(List<DrugInOrder> drugsInOrder, long orderId, long pharmacyId) {
		for(DrugInOrder drugInOrder : drugsInOrder) {
			drugInOrder.getId().setOrderId(orderId);
			drugInPharmacyService.addDrugToPharmacy(new DrugInPharmacy(new DrugInPharmacyId(pharmacyId, drugInOrder.getId().getDrugId()),
					drugInOrder.getQuantity()), true);
		}
		
		drugInOrderRepository.saveAll(drugsInOrder);
	}
	
	public void removeDrugsFromOrderAndPharmacy(List<DrugInOrder> drugsInOrder, long orderId, long pharmacyId) {
		for(DrugInOrder drugInOrder : drugsInOrder) {
			drugInPharmacyService.removeOrderedDrugFromPharmacy(drugInOrder.getId().getDrugId(), pharmacyId);
		}
		
		drugInOrderRepository.deleteAll(drugsInOrder);
	}
	
	public boolean checkIfOrderHasOffers(long orderId) {
		return !offerRepository.findAllByOrderId(orderId).isEmpty();
	}
}
