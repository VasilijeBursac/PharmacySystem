package ISA.Team54.users.service.interfaces;

import java.util.List;

import ISA.Team54.users.model.Pharmacist;

public interface PharmacistService {
	Pharmacist findOneById(long id);
	List<Pharmacist> getAllPharmacists();
	List<Pharmacist> getAllPharmacistsInPharmacy(long pharmacyId);
	void removePharmacistFromPharmacy(long pharmacistId) throws Exception;
}
