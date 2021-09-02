package ISA.Team54.vacationAndWorkingTime.service.interfaces;

import java.util.List;

import ISA.Team54.shared.model.DateRange;
import ISA.Team54.vacationAndWorkingTime.model.VacationRequest;

public interface VacationRequestService {
	void scheduleVacation(DateRange dateRange);
	List<VacationRequest> getAllVacationRequestsForPharmacy(long pharmacyId);
	void respondToVacationRequest(long vacationRequestId, boolean isApproved, String responseMessage);
}
