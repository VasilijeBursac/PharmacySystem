package ISA.Team54.vacationAndWorkingTime.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;
import ISA.Team54.vacationAndWorkingTime.repository.DermatologistWorkScheduleRepository;
import ISA.Team54.vacationAndWorkingTime.service.interfaces.DermatologistWorkScheduleService;

@Service
public class DermatologistWorkScheduleServiceImpl implements DermatologistWorkScheduleService{
	
	@Autowired 
	private DermatologistWorkScheduleRepository dermatologistWorkScheduleRepository;
	
	
	@Override
	public DermatologistWorkSchedule getDermatologistWorkScheduleInPharmacy(long dermatologistId, long pharmacyId) {
		return dermatologistWorkScheduleRepository.getByDermatologistIdPharmacyId(dermatologistId, pharmacyId);
	}

}
