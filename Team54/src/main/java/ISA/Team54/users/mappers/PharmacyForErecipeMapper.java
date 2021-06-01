package ISA.Team54.users.mappers;

import ISA.Team54.users.dto.PharmacyForERecipeDTO;
import ISA.Team54.users.model.Pharmacy;

public class PharmacyForErecipeMapper {

	public static PharmacyForERecipeDTO PharmacyToPharmacyForErecipeDTO(Pharmacy pharmacy,float totalPrice) {
		return new PharmacyForERecipeDTO(pharmacy.getId(),
										pharmacy.getName(),
										pharmacy.getRatings(),
										totalPrice,
										pharmacy.getCity());
	}
}
