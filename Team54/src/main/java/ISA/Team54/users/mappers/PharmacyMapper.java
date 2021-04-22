package ISA.Team54.users.mappers;

import ISA.Team54.users.dto.PharmacyDTO;
import ISA.Team54.users.dto.UserRequestDTO;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.model.User;

public class PharmacyMapper {
		
	public static Pharmacy PharmacyDTOToPharmacy(PharmacyDTO pharmacyDTO) {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setName(pharmacyDTO.getName());	
		pharmacy.setAddress(pharmacyDTO.getAddress());
		pharmacy.setCity(pharmacyDTO.getCity());
		pharmacy.setCountry(pharmacyDTO.getCountry());
		pharmacy.setDescription(pharmacyDTO.getDescription());
		pharmacy.setPharmacistPrice(pharmacyDTO.getPharmacistPrice());
		return pharmacy;						
	}
	
	public static PharmacyDTO PharmacyToPharmacyDTO(Pharmacy pharmacy) {
		return new PharmacyDTO(
				pharmacy.getId(),
				pharmacy.getName(),
				pharmacy.getAddress(),
				pharmacy.getCity(),
				pharmacy.getCountry(),
				pharmacy.getRatings(),
				pharmacy.getPharmacistPrice(),
				pharmacy.getDescription());
	}
	
	public static void PharmacyDTOToPharmacyUpdateInfo(PharmacyDTO pharmacyDTO, Pharmacy pharmacy) {
		pharmacy.setName(pharmacyDTO.getName());
		pharmacy.setDescription(pharmacyDTO.getDescription());
		pharmacy.setAddress(pharmacyDTO.getAddress());
		pharmacy.setCity(pharmacyDTO.getCity());
		pharmacy.setCountry(pharmacyDTO.getCountry());
	}
}
