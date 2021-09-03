package ISA.Team54.vacationAndWorkingTime.service.interfaces;

import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;

public interface DermatologistWorkScheduleService {
	DermatologistWorkSchedule getDermatologistWorkScheduleInPharmacy(long dermatologistId, long pharmacyId);
}
