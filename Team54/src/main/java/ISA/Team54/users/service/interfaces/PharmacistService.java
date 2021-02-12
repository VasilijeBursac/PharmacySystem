package ISA.Team54.users.service.interfaces;

import java.util.List;

import ISA.Team54.users.dto.PharmacistRequestDTO;

public interface PharmacistService {
	List<PharmacistRequestDTO> getAllPharmacistsInPharmacy(long pharmacyId);
}
