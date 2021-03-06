package ISA.Team54.Examination.service.implementation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.Examination.dto.EmployeeExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationForCalendarDTO;
import ISA.Team54.Examination.dto.ExaminationInformationDTO;
import ISA.Team54.Examination.enums.ExaminationStatus;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.mapper.ExaminationForCalendarMapper;
import ISA.Team54.Examination.mapper.ExaminationMapper;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.model.Term;
import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.exceptions.InvalidTimeLeft;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.shared.model.DateRange;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.enums.UserRole;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.model.User;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.users.repository.UserRepository;
import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;
import ISA.Team54.vacationAndWorkingTime.repository.DermatologistWorkScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExaminationServiceImpl implements ExaminationService {
	final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
	@Autowired
	private ExaminationRepository examinationRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DrugRepository drugRepository;
	@Autowired
	private DrugService drugService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DermatologistRepository dermatologistRepository;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private DermatologistWorkScheduleRepository dermatologistWorkScheduleRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LoyaltyRepository loyaltyRepository;

	public Long getCurrentEmployedId() {
		ExaminationType examinaitonType = ExaminationType.DermatologistExamination;
		if(getCurrentRole().equals(UserRole.ROLE_PHARMACIST)) {
			examinaitonType = ExaminationType.PharmacistExamination;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long userId =  ((User) authentication.getPrincipal()).getId();
		return userId;
	}

	public UserRole getCurrentRole() {
		ExaminationType examinaitonType = ExaminationType.DermatologistExamination;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			Pharmacist pharmacist = pharmacistRepository
					.findOneById(((Pharmacist) authentication.getPrincipal()).getId());
			if (pharmacist != null)
				return UserRole.ROLE_PHARMACIST;
		} catch (Exception e) {
			return UserRole.ROLE_DERMATOLOGIST;
		}
		return UserRole.ROLE_DERMATOLOGIST;
	}

	@Override
	public Examination getCurrentExaminationForEmployee() {
		// ,ExaminationStatus.Unfille nedostaje deo sa statusom
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -20);
		Date twentyMinutesBack = cal.getTime();
		cal.add(Calendar.HOUR, +325);
		Date fourHoursFront = cal.getTime();
		List<Examination> employeeExaminations = examinationRepository.getSoonestDates(getCurrentEmployedId(), ExaminationStatus.Filled,twentyMinutesBack,fourHoursFront);
		if (employeeExaminations.size() <= 0) {
			return null;
		}
	
		if (employeeExaminations == null) {
			return null;
		}
		Examination soonestExamination = employeeExaminations.get(0);
		
		for (Examination examination : employeeExaminations) {
			long millisExamination = examination.getTerm().getStart().getTime();
			long millisSoonestExamination = soonestExamination.getTerm().getStart().getTime();
			if (examination.getTerm().getStart().getTime()<millisSoonestExamination)
			{
				soonestExamination = examination;
			}
		}
		return soonestExamination;
	}

	public int isPatientAppropriate(Long patientId) {
		try {
			if (getCurrentExaminationForEmployee().getPatient().getId() == patientId)
				return 1;
			else
				return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<User> getEmployeeWhoExaminedPatient(ExaminationType type) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		List<Examination> examinations =  examinationRepository.findByPatientId(patient.getId());
		List<User> employees = new ArrayList<User>();
		examinations.forEach(
				e -> {
					if(e.getType() == type){
						User employee = userRepository.findOneById(e.getEmplyeedId());
						if(CheckIfEmployeeUnique(employee, employees))
							employees.add(employee);
					}
				}
		);
		return employees;
	}

	private boolean CheckIfEmployeeUnique(User employee, List<User> employees) {
		for (User user : employees) {
			if(user.getId() == employee.getId())
				return false;
		}
		return true;
	}

	@Override
	public List<Examination> getPatientExaminationsByType(ExaminationType type){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		List<Examination> examinations =  examinationRepository.getHistoryExaminationsForPatient(patient.getId(), type, ExaminationStatus.Filled);
		return examinations;
	}

	@Override
	public List<Examination> historyOfPatientExamination(Long id) {

		ExaminationType examinaitonType = ExaminationType.DermatologistExamination;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			Pharmacist pharmacist = pharmacistRepository.findOneById(((Pharmacist) authentication.getPrincipal()).getId());
		if(pharmacist != null) 
			examinaitonType = ExaminationType.PharmacistExamination;;
		}catch(Exception e) {
			examinaitonType = ExaminationType.DermatologistExamination;;
		}finally{
			List<Examination> examinationHistories = examinationRepository.getHistoryExaminationsForPatient(id, examinaitonType, ExaminationStatus.Filled);
			return examinationHistories;
		}
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateExamination(ExaminationInformationDTO examinationInformationDTO) throws Exception {
		Examination examination = examinationRepository.findOneById((examinationInformationDTO.getId()));
		List<Drug> drugsForExamination = new ArrayList<Drug>();
		if (examinationInformationDTO.getDrugs() != null) {
			for (DrugDTO d : examinationInformationDTO.getDrugs()) {
				drugsForExamination.add(drugRepository.findOneById(d.getId()));
					drugService.reduceDrugQuantityInPharmacy(d.getId(), (int) examination.getPharmacy().getId(), 1);
			}
			;
			examination.setDrugs(drugsForExamination);
		}
		examination.setTherapyDuration(examinationInformationDTO.getTherapyDuration());
		examination.setDiagnose(examinationInformationDTO.getDiagnosis());
		examination.setStatus(ExaminationStatus.Filled);
		examinationRepository.save(examination);
	}

	@Override
	public List<Examination> getAllExaminatedExaminationsForEmployee() {
		List<Examination> examinations = new ArrayList<Examination>();
		for (Examination e : examinationRepository.findByEmplyeedIdAndStatus(getCurrentEmployedId(), ExaminationStatus.Filled)) {
			if (e.getTerm().getStart().before(new Date())) {
				Patient p = e.getPatient();
				e.setPatient(p);
				examinations.add(e);
			}
		}
		return examinations;
	}

	public List<Examination> getDefinedExaminations() {
		Examination examination = getCurrentExaminationForEmployee();
		List<Examination> definedExaminations = new ArrayList<Examination>();
		for (Examination ex : examinationRepository.findByEmplyeedIdAndStatusAndPharmacyId(examination.getEmplyeedId(),
				ExaminationStatus.Unfilled, examination.getPharmacy().getId())) {
			if (examination.getPatient() != null) {
				definedExaminations.add(ex);
			}
		}
		return definedExaminations;
	}

	@Override
	public List<EmployeeExaminationDTO> getAllExaminationsForPharmacy(long id, ExaminationType type) {
		List<Examination> examinations = examinationRepository.getAllExaminationsForPharmacy(id, type,
				ExaminationStatus.Unfilled);
		List<User> employees = new ArrayList<User>();
		examinations.forEach(e -> employees.add(userRepository.findOneById(e.getEmplyeedId())));

		List<EmployeeExaminationDTO> examinationDTOs = new ArrayList<EmployeeExaminationDTO>();
		for (int i = 0; i < examinations.size(); i++) {
			examinationDTOs.add(new ExaminationMapper().ExaminationToEmployeeExaminationDTO(examinations.get(i),
					employees.get(i), type));
		}

		return examinationDTOs;
	}

	@Override
	public List<EmployeeExaminationDTO> getExaminationsForPharmacyAndDate(long id, ExaminationType type,
			Date date) {
		List<Examination> examinations = examinationRepository.getExaminationsForPharmacyForDate(id, type,
				ExaminationStatus.Unfilled, date);
		List<User> employees = new ArrayList<User>();
		examinations.forEach(e -> employees.add(userRepository.findById(e.getEmplyeedId()).orElse(null)));

		List<EmployeeExaminationDTO> examinationDTOs = new ArrayList<EmployeeExaminationDTO>();
		for (int i = 0; i < examinations.size(); i++) {
			examinationDTOs.add(new ExaminationMapper().ExaminationToEmployeeExaminationDTO(examinations.get(i),
					employees.get(i), type));
		}

		return examinationDTOs;
	}

	@Transactional(readOnly = false)
	@Override
	public void scheduleExamination(long id) throws PessimisticLockingFailureException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		Examination examination = examinationRepository.findById(id).orElse(null);
		if (examination != null) {
			examination.setStatus(ExaminationStatus.Filled);
			examination.setPatient(patient);			
			examinationRepository.save(examination);
			new Thread(() -> {
				emailService.sendEmail("tim54isa@gmail.com", "Zakazan pregled", "Uspesno ste zakazali pregled!");

			}).start();
		}
	}

	@Override
	public void cancelExamination(long id) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		Examination examination = examinationRepository.findById(id).orElse(null);
		if (examination != null) {
			if (examination.getTerm().getStart().getTime() - new Date().getTime() > 24 * 60 * 60 * 1000) {
				examination.setStatus(ExaminationStatus.Unfilled);
				examination.setPatient(null);

				examinationRepository.save(examination);
			} else
				throw new InvalidTimeLeft();
		} else
			throw new Exception();
	}

	@Override
	public List<EmployeeExaminationDTO> getFutureExaminations(ExaminationType type) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		List<Examination> examinations = examinationRepository.getFutureExaminations(patient.getId(), type,
				ExaminationStatus.Filled);
		List<User> employees = new ArrayList<User>();
		examinations.forEach(e -> employees.add(userRepository.findById(e.getEmplyeedId()).orElse(null)));

		List<EmployeeExaminationDTO> examinationDTOs = new ArrayList<EmployeeExaminationDTO>();
		for (int i = 0; i < examinations.size(); i++) {
			examinationDTOs.add(new ExaminationMapper().ExaminationToEmployeeExaminationDTO(examinations.get(i),
					employees.get(i), type));
		}

		return examinationDTOs;
	}

	public boolean isDermatologistOnWorkInTheParmacy(Long employeeId, Long pharmacyId, DateRange examinationTime) {
		Dermatologist dermatologist = dermatologistRepository.findOneById(employeeId);
		List<DermatologistWorkSchedule> workingSchedules;
		if(dermatologist != null) {
			workingSchedules= dermatologistWorkScheduleRepository.findByDermatologistIdAndPharmacyId(employeeId, pharmacyId);
			for (DermatologistWorkSchedule workingSchedule : workingSchedules) {
				if (workingSchedule.getPharmacy().getId() == pharmacyId
						&& examinationTime.isInRange(new DateRange(workingSchedule.getTimePeriod().getStartDate(),
								workingSchedule.getTimePeriod().getEndDate()))) {
					return true;
				}
			} 
			return false;
		}else { 
			Pharmacist pharmacist = pharmacistRepository.findOneById(employeeId);
			int workingDayEnd = pharmacist.getWorkSchedule().getStartDate().getHours() *60 + pharmacist.getWorkSchedule().getStartDate().getMinutes();
			int workingDayStart = pharmacist.getWorkSchedule().getEndDate().getHours()*60 +pharmacist.getWorkSchedule().getEndDate().getMinutes() ;
			int examinationStart = examinationTime.getStartDate().getHours()*60 + examinationTime.getStartDate().getMinutes();
			int examinationEnd = (int)examinationTime.getEndDate().getHours()*60+(int)examinationTime.getEndDate().getMinutes();
			
			if(examinationStart>=workingDayStart && examinationStart < workingDayEnd && examinationEnd>workingDayStart && examinationEnd<=workingDayEnd)
				return true;
			return false;
//			if (examinationTime.isInRange(new DateRange(pharmacist.getWorkSchedule().getStartDate(),
//					pharmacist.getWorkSchedule().getEndDate()))) {

//				return true;
//			}else {
//				return false;
//			}
		}	
	}

	public boolean isDermatologistAvailable(Long dermatologistId, Long pharmacyId, Date start, Date end) {
		if (!isDermatologistOnWorkInTheParmacy(dermatologistId, pharmacyId, new DateRange(start, end))) {
			return false;
		}
		for (Examination dermatologistExamination : examinationRepository.findByEmplyeedIdAndPharmacyId(dermatologistId,
				pharmacyId)) {
			Term term = dermatologistExamination.getTerm();
			if ((new DateRange(start, end)).isTheDateBetweenDates(term.getStart())) {
				return false;
			}
		}
		return true;
	}

	public boolean isPatientAvailable(Long patientId, Date start, Date end) {

		for (Examination examination : examinationRepository.findByPatientId(patientId)) {
			Term term = examination.getTerm();
			if ((new DateRange(start, end)).isTheDateBetweenDates(term.getStart())) {
				return false;
			}
		}
		return true;
	}

	public boolean canExaminationBeScheduled(Examination examination, Date start, Date end) {
		if (!isDermatologistAvailable(examination.getEmplyeedId(), examination.getPharmacy().getId(), start, end))
			return false;
		if (!isPatientAvailable(examination.getPatient().getId(), start, end)) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<EmployeeExaminationDTO> getExaminationsForPharmacy(long id, ExaminationType type) {
		List<Examination> examinations = examinationRepository.getAllFutureExaminationsForPharmacy(id, type, ExaminationStatus.Unfilled);
		List<User> employees = new ArrayList<User>();
		examinations.forEach(
				e -> employees.add(userRepository.findById(e.getEmplyeedId()).orElse(null))
		);

		List<EmployeeExaminationDTO> examinationDTOs = new ArrayList<EmployeeExaminationDTO>();
		ExaminationMapper mapper = new ExaminationMapper();
		for(int i = 0; i < examinations.size(); i++) {
			examinations.get(i).setPrice(getExaminationPriceWithDiscount(examinations.get(i).getPrice()));
			examinationDTOs.add(mapper.ExaminationToEmployeeExaminationDTO(examinations.get(i), employees.get(i), type));
		}

		return examinationDTOs;
	}
	
	private int getExaminationPriceWithDiscount(int price) {		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		return  (int) (0.01 * price * (100 - loyaltyRepository.getLoyaltyCategory(patient.getLoyaltyPoints()).getDiscount()));
		    
	}

	public boolean scheduleExamination(Date start) {
		long curTimeInMs = start.getTime();
		Date end = new Date(curTimeInMs + (30 * ONE_MINUTE_IN_MILLIS));
		Examination examination = examinationRepository.findOneById(getCurrentExaminationForEmployee().getId());
		if (!canExaminationBeScheduled(examination, start, end)) {
			return false;
		}
		Examination newExamination = new Examination();
		newExamination.setPrice(examination.getPrice());
		newExamination.setType(examination.getType());
		newExamination.setStatus(ExaminationStatus.Filled);
		newExamination.setEmplyeedId(examination.getEmplyeedId());
		newExamination.setPatient(examination.getPatient());
		newExamination.setTerm(new Term(start, 30));
		newExamination.setPharmacy(examination.getPharmacy());
		emailService.sendEmail("tim54isa@gmail.com", "ZAKAZAN PREGLED",
				"Vas naredni pregled je zakazan : " + newExamination.getTerm().getStart());
		examinationRepository.save(newExamination);

		return true;
	}
	@Transactional(readOnly = false)
	public boolean saveExamination(Long newExaminationId) {
		Long currentExaminationId = getCurrentExaminationForEmployee().getId();
		Examination currentExamination = examinationRepository.findOneById(currentExaminationId);
		Examination newExamination = examinationRepository.findOneById(newExaminationId);
		newExamination.setStatus(ExaminationStatus.Filled);
		newExamination.setPatient(currentExamination.getPatient());
		examinationRepository.save(newExamination);
		emailService.sendEmail("tim54isa@gmail.com", "PREGLED",
				"Vas naredni pregled je zakazan : " + newExamination.getTerm().getStart());
		return true;
	}

	@Override
	public List<Pharmacy> getFreePharmaciesForInterval(Date term, ExaminationType type) {
		List<Examination> examinations = examinationRepository.getFreeExaminationsForInterval(term, type);
		System.out.println(term);
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		examinations.forEach(e -> {
			if (isPharmacyUnique(e.getPharmacy(), pharmacies))
				pharmacies.add(e.getPharmacy());
		});

		return pharmacies;
	}

	private boolean isPharmacyUnique(Pharmacy pharmacy, List<Pharmacy> pharmacies) {
		for (int i = 0; i < pharmacies.size(); i++) {
			if (pharmacies.get(i).getId() == pharmacy.getId())
				return false;
		}
		return true;
	}

	@Override
	public List<ExaminationForCalendarDTO> getExaminaitonForCalendar() {
		List<ExaminationForCalendarDTO> examinationsForCalendar = new ArrayList<ExaminationForCalendarDTO>();
		List<Examination> examinations = examinationRepository.findByEmplyeedId(getCurrentEmployedId());
		for (Examination examination : examinations) {
			examinationsForCalendar
					.add(new ExaminationForCalendarMapper().examinationForExaminationForCalendarDTO(examination));
		}
		return examinationsForCalendar;
	}

}
