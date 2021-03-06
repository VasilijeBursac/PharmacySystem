package ISA.Team54.users.service.interfaces;

import java.util.List;

import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.model.Pharmacist;

public interface PharmacistService {
	Pharmacist findOneById(long id); 
	List<PharmacistRequestDTO> getAllPharmacistsInPharmacy(long pharmacyId);
	
}
