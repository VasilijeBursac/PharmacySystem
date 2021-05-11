package ISA.Team54.drugOrdering.service.interfaces;

import java.util.List;

import ISA.Team54.drugOrdering.exceptions.NotEnoughDrugsInStorageException;
import ISA.Team54.drugOrdering.model.Offer;

public interface OfferService {
	void addOffer(Offer offer) throws NotEnoughDrugsInStorageException;
	List<Offer> findAllOffersForSupplier();
}
