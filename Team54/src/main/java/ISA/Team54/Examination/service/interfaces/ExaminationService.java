package ISA.Team54.Examination.service.interfaces;
import java.util.Date;
import java.util.List;

import ISA.Team54.Examination.dto.EmployeeExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationForCalendarDTO;
import ISA.Team54.Examination.dto.ExaminationInformationDTO;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.shared.model.DateRange;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.enums.UserRole;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.model.User;
import org.springframework.dao.PessimisticLockingFailureException;

public interface ExaminationService {
	Examination getCurrentExaminationForEmployee();
	List<Examination> historyOfPatientExamination(Long id) throws Exception;
	void updateExamination(ExaminationInformationDTO examinationInformationDTO) throws Exception;
	List<Examination> getAllExaminatedExaminationsForEmployee();
	List<Examination> getDefinedExaminations();
	List<EmployeeExaminationDTO> getAllExaminationsForPharmacy(long id, ExaminationType type);
	List<EmployeeExaminationDTO> getExaminationsForPharmacyAndDate(long id, ExaminationType type, Date date);
	void scheduleExamination(long id);
	boolean canExaminationBeScheduled(Examination examination, Date start,Date end);
	boolean isDermatologistOnWorkInTheParmacy(Long employeeId, Long pharmacyId, DateRange examinationTime);
	boolean scheduleExamination(Date start) throws PessimisticLockingFailureException;
	void cancelExamination(long id) throws Exception;
	boolean isPatientAvailable(Long patientId, Date start, Date end);
	boolean saveExamination(Long newExaminationId);
	List<EmployeeExaminationDTO> getFutureExaminations(ExaminationType type);
	List<Pharmacy> getFreePharmaciesForInterval(Date term, ExaminationType type);
	int isPatientAppropriate(Long patientId);
    List<User> getEmployeeWhoExaminedPatient(ExaminationType type);
	List<ExaminationForCalendarDTO> getExaminaitonForCalendar();
	boolean isDermatologistAvailable(Long dermatologistId, Long pharmacyId, Date start, Date end);
	UserRole getCurrentRole();
	Long getCurrentEmployedId();
	List<Examination> getPatientExaminationsByType(ExaminationType type);

	List<EmployeeExaminationDTO> getExaminationsForPharmacy(long id, ExaminationType type);
	
	boolean checkIfEmployeeHasScheduledExaminationsInFuture(long pharmacistId, long pharmacyId);
}
