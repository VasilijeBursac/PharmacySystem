package ISA.Team54.drugOrdering.mapper;

import ISA.Team54.drugOrdering.dto.OfferDTO;
import ISA.Team54.drugOrdering.enums.OfferStatus;
import ISA.Team54.drugOrdering.model.Offer;

public class OfferMapper {

	public static Offer OfferDTOIntoOffer(OfferDTO offerDTO) {
		return new Offer(offerDTO.getTotalPrice(), offerDTO.getDeliveryDeadline());
	}
	
	public static OfferDTO OfferIntoOfferDTO(Offer offer) {
		String status = "";
		switch(offer.getStatus()) {
			case Accepted:
				status = "Prihvaćena";
				break;
			case Waiting:
				status = "Čeka na odgovor";
				break;
			default:
				status = "Odbijena";
		}
		return new OfferDTO(
							offer.getId(),
							offer.getTotalPrice(),
							offer.getDeliveryDeadline(),
							status,
							offer.getOrder().getAdministrator().getPharmacy().getName(),
							offer.getSupplier().getName() + " " + offer.getSupplier().getSurname(),
							offer.getOrder().getId());
	}
}
