package ISA.Team54.users.mapper;

import ISA.Team54.users.dto.DermatologistInfoDTO;
import ISA.Team54.users.mappers.PharmacyMapper;
import ISA.Team54.users.model.Dermatologist;

public class DermatologistMapper {
	public static DermatologistInfoDTO DermatologistToDermatologistInfoDTO(Dermatologist dermatologist) {
		return new DermatologistInfoDTO(
				dermatologist.getName(),
				dermatologist.getSurname(),
				dermatologist.getEmail(),
				dermatologist.getPhoneNumber(),
				dermatologist.getAddress(),
				dermatologist.getCity(),
				dermatologist.getCountry(),
				dermatologist.getPrice(),
				dermatologist.getRatings(),
				PharmacyMapper.PharmaciesToPharmaciesDTOs(dermatologist.getPharmacy())
		);
	}
}
