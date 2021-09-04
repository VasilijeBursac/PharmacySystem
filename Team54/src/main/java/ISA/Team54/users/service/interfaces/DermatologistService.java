package ISA.Team54.users.service.interfaces;


import java.util.List;

import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.dto.DermatologistToPharmacyDTO;
import ISA.Team54.users.model.Dermatologist;

public interface DermatologistService {

	List<Dermatologist> findAll();
	Dermatologist findOneById(Long id);
	List<Dermatologist> getAllDermatologists();
	List<Dermatologist> getAllDermatologistsInPharmacy(long pharmacyId);
	List<Dermatologist> getAllDermatologistsNotInPharmacy(long pharmacyId);
	void removeDermatologistFromPharmacy(long dermatologistId, long pharmacyId) throws Exception;
	void addDermatologistToPharmacy(DermatologistToPharmacyDTO dermatologistToPharmacyDTO, long pharmacyId) throws Exception;
}
