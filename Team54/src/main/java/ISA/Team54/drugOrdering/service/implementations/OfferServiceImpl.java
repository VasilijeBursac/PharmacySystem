package ISA.Team54.drugOrdering.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.DrugInStorage;
import ISA.Team54.drugAndRecipe.repository.DrugInStorageRepository;
import ISA.Team54.drugOrdering.exceptions.NotEnoughDrugsInStorageException;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.Offer;
import ISA.Team54.drugOrdering.repository.DrugInOrderRepository;
import ISA.Team54.drugOrdering.repository.OfferRepository;
import ISA.Team54.drugOrdering.service.interfaces.OfferService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Supplier;
import ISA.Team54.users.repository.SupplierRepository;


@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	DrugInOrderRepository drugInOrderRepository;
	
	@Autowired
	DrugInStorageRepository drugInStorageRepository;
	
	@Override
	public void addOffer(Offer offer) throws NotEnoughDrugsInStorageException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Supplier supplier = supplierRepository.findById(((Supplier) authentication.getPrincipal()).getId());
		offer.setSupplier(supplier);
		if(!checkDrugsInStorageForOrder(offer.getOrder().getId(),supplier)) { 
			System.out.println(checkDrugsInStorageForOrder(offer.getOrder().getId(),supplier) + "-------------");
			throw new NotEnoughDrugsInStorageException();	
		}		
		offerRepository.save(offer);
	}
	
	private boolean checkDrugsInStorageForOrder(long orderId, Supplier supplier) {
		for (DrugInOrder drugInOrder : drugInOrderRepository.findAllByIdOrderId(orderId)) {
			for (DrugInStorage drugInStorage : drugInStorageRepository.findAllBySupplier(supplier)) {
				if(drugInOrder.getId().getDrugId() == drugInStorage.getDrug().getId() && drugInOrder.getQuantity() > drugInStorage.getQuantity())
					return false;
			}
		}	
		return true;
	}

	@Override
	public List<Offer> findAllOffersForSupplier() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Supplier supplier = supplierRepository.findById(((Supplier) authentication.getPrincipal()).getId());
		if(offerRepository.findAllBySupplier(supplier).size() != 0)
			return offerRepository.findAllBySupplier(supplier);
		return null;
	}
}


