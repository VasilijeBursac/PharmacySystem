package ISA.Team54.promotion.service.interfaces;

import java.util.List;

import ISA.Team54.promotion.dto.PromotionDTO;
import ISA.Team54.promotion.model.Promotion;

public interface PromotionService {
	List<Promotion> getAllPromotionsForPharmacy(long pharmacyId);
	void addNewPromotion(Promotion promotion);
	void deletePromotionById(long promotionId);
}
