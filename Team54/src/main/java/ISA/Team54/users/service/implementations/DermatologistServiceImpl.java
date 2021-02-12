package ISA.Team54.users.service.implementations;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PharmacyRepository;
import ISA.Team54.users.service.interfaces.DermatologistService;

@Service
public class DermatologistServiceImpl implements DermatologistService {
	
	@Autowired
	DermatologistRepository dermatologistRepository;
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@Override
	public List<Dermatologist> findAll() {
		return this.dermatologistRepository.findAll();
	}

	@Override
	public Dermatologist findOneById(Long id) {
		return dermatologistRepository.findOneById(id);
	}

	@Override
	public List<DermatologistRequestDTO> getAllDermatologistsInPharmacy(long pharmacyId) {
		List<Dermatologist> dermatologistsInPharmacy = pharmacyRepository.findById(pharmacyId).getDermatologists();
		return null;
	}	

}
