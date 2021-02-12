package ISA.Team54.users.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.users.service.interfaces.PharmacistService;

@Service
public class PharmacistServiceImpl implements PharmacistService{
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Override
	public List<PharmacistRequestDTO> getAllPharmacistsInPharmacy(long pharmacyId) {
		List<Pharmacist> pharmacistsInPharmacy = pharmacistRepository.findAllByPharmacyId(pharmacyId);
		return null;
	}

}
