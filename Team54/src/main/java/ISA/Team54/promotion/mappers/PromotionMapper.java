package ISA.Team54.promotion.mappers;

import ISA.Team54.promotion.dto.PromotionDTO;
import ISA.Team54.promotion.model.Promotion;
import ISA.Team54.users.model.Pharmacy;

public class PromotionMapper {
	public static Promotion PromotionDTOAndPharmacyToPromotion(PromotionDTO promotionDTO, Pharmacy pharmacy) {
		return new Promotion(
				promotionDTO.getDescription(),
				promotionDTO.getDateInterval(),
				pharmacy);
	}
	
	public static PromotionDTO PromotionToPromotionDTO(Promotion promotion) {
		return new PromotionDTO(
				promotion.getId(),
				promotion.getDescription(),
				promotion.getDateInterval(),
				promotion.getPharmacy().getId());
	}
}
