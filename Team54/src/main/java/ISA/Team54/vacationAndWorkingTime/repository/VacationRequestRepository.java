package ISA.Team54.vacationAndWorkingTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ISA.Team54.vacationAndWorkingTime.model.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>{
	VacationRequest findOneById(long vacationRequestId);
	
	@Query("select vr from VacationRequest vr where vr.pharmacist != null and vr.pharmacist.pharmacy.id = ?1")
	List<VacationRequest> getPharmacistsVacationRequestsByPharmacyId(long pharmacyId);
	
	@Query("select vr from VacationRequest vr where vr.dermatologist != null")
	List<VacationRequest> getDermatologistsVacationRequests();
	
	@Query("select vr from VacationRequest vr where (vr.dermatologist.id = ?1 or vr.pharmacist.id = ?1) and vr.status='Approved'")
	List<VacationRequest> getAllApprovedVacationRequestsForEmployee(long employeeId);
	
	
}
