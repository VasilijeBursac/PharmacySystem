package ISA.Team54.users.service.interfaces;


import java.util.List;

import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.model.Dermatologist;

public interface DermatologistService {

	List<Dermatologist> findAll();
	Dermatologist findOneById(Long id);
	List<DermatologistRequestDTO> getAllDermatologistsInPharmacy(long pharmacyId);

}
