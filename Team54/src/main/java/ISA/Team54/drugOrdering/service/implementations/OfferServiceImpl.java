package ISA.Team54.drugOrdering.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.DrugInStorage;
import ISA.Team54.drugAndRecipe.repository.DrugInStorageRepository;
import ISA.Team54.drugAndRecipe.service.impl.ClockImpl;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugAndRecipe.service.interfaces.IClock;
import ISA.Team54.drugOrdering.enums.OfferStatus;
import ISA.Team54.drugOrdering.enums.OrderStatus;
import ISA.Team54.drugOrdering.exceptions.DeadlineNotOverException;
import ISA.Team54.drugOrdering.exceptions.NotEnoughDrugsInStorageException;
import ISA.Team54.drugOrdering.exceptions.NotOrderAuthorException;
import ISA.Team54.drugOrdering.exceptions.OrderAlreadyFulfilledException;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.Offer;
import ISA.Team54.drugOrdering.repository.DrugInOrderRepository;
import ISA.Team54.drugOrdering.repository.DrugOrderRepository;
import ISA.Team54.drugOrdering.repository.OfferRepository;
import ISA.Team54.drugOrdering.service.interfaces.OfferService;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.PharmacyAdministrator;
import ISA.Team54.users.model.Supplier;
import ISA.Team54.users.repository.SupplierRepository;


@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	DrugOrderRepository drugOrderRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	DrugInOrderRepository drugInOrderRepository;
	
	@Autowired
	DrugInStorageRepository drugInStorageRepository;
	
	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void addOffer(Offer offer) throws NotEnoughDrugsInStorageException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Supplier supplier = supplierRepository.findById(((Supplier) authentication.getPrincipal()).getId());
		offer.setSupplier(supplier);
		if(!checkDrugsInStorageForOrder(offer.getOrder().getId(),supplier))
			throw new NotEnoughDrugsInStorageException();	
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

	@Override
	public List<Offer> getAllOffersForOrder(long orderId) {
		return offerRepository.findAllByOrderId(orderId);
	}

	@Override
	public void acceptOffer(long offerId) throws Exception {
		Offer selectedOffer = offerRepository.findOneById(offerId);
		long orderAuthorId = selectedOffer.getOrder().getAdministrator().getId();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(selectedOffer.getOrder().getStatus() == OrderStatus.Fulfilled)
			throw new OrderAlreadyFulfilledException();
		
		if(((PharmacyAdministrator) authentication.getPrincipal()).getId() != orderAuthorId)
			throw new NotOrderAuthorException();
		
		IClock clock = new ClockImpl();
		if(clock.getDate().getTime() <= selectedOffer.getOrder().getDeadline().getTime())
			throw new DeadlineNotOverException();
		
		List<Offer> allOffers = getAllOffersForOrder(selectedOffer.getOrder().getId());

		for(Offer offer : allOffers) {
			if(offer.getId() == selectedOffer.getId()) 
				offer.setStatus(OfferStatus.Accepted);
			else 
				offer.setStatus(OfferStatus.Declined);
			
			String pharmacyName = offer.getOrder().getAdministrator().getPharmacy().getName();
			new Thread(() -> {
				emailService.sendEmail(offer.getSupplier().getEmail(), 
						 "Obrađena ponuda za narudžbenicu",
						 "Vaša ponuda za narudžbenicu apoteke " + pharmacyName
						 + " je " + (offer.getStatus() == OfferStatus.Accepted ? "prihvaćena." : "odbijena."));
			}).start();
		}
		
		offerRepository.saveAll(allOffers);
		selectedOffer.getOrder().setStatus(OrderStatus.Fulfilled);
		drugOrderRepository.save(selectedOffer.getOrder());
		
		drugInPharmacyService.updateDrugsQuantities(drugInOrderRepository.findAllByIdOrderId(selectedOffer.getOrder().getId()));
	}
}


