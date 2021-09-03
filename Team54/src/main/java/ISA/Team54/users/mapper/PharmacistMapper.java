package ISA.Team54.users.mapper;

import ISA.Team54.users.dto.PharmacistInfoDTO;
import ISA.Team54.users.model.Pharmacist;

public class PharmacistMapper {
	public static PharmacistInfoDTO PharmacistToPharmacistInfoDTO(Pharmacist pharmacist) {
		return new PharmacistInfoDTO(
				pharmacist.getId(),
				pharmacist.getName(),
				pharmacist.getSurname(),
				pharmacist.getEmail(),
				pharmacist.getPhoneNumber(),
				pharmacist.getAddress(),
				pharmacist.getCity(),
				pharmacist.getCountry(),
				pharmacist.getRatings(),
				pharmacist.getPharmacy().getId(),
				pharmacist.getPharmacy().getPharmacistPrice(),
				pharmacist.getPharmacy().getName()
		);
	}
}
