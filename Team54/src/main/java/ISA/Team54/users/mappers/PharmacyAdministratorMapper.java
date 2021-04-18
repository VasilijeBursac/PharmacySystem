package ISA.Team54.users.mappers;

import ISA.Team54.users.dto.PharmacyAdministratorDTO;
import ISA.Team54.users.model.PharmacyAdministrator;

public class PharmacyAdministratorMapper {

	public static PharmacyAdministratorDTO pharmacyAdministratorToPharmacyAdministratorDTO(PharmacyAdministrator pharmacyAdmin) {
		return new PharmacyAdministratorDTO((int)pharmacyAdmin.getId(), pharmacyAdmin.getEmail(), pharmacyAdmin.getName(), pharmacyAdmin.getSurname(), pharmacyAdmin.getAddress(), pharmacyAdmin.getCity(), pharmacyAdmin.getCountry(), pharmacyAdmin.getPhoneNumber(), pharmacyAdmin.getConfirmed(), (int)pharmacyAdmin.getPharmacy().getId());
	}
}
