package ISA.Team54.Examination.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.Examination.dto.DefinedExaminationDTO;
import ISA.Team54.Examination.dto.EmployeeExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationForCalendarDTO;
import ISA.Team54.Examination.dto.ExaminationInformationDTO;
import ISA.Team54.Examination.dto.ExaminationTypeDTO;
import ISA.Team54.Examination.dto.NewExaminationDTO;
import ISA.Team54.Examination.dto.ScheduleExaminaitonDTO;
import ISA.Team54.Examination.dto.StartExaminationDTO;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.mapper.DefinedExamiantionMapper;
import ISA.Team54.Examination.mapper.ExaminationMapper;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.exceptions.InvalidTimeLeft;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.dto.UserInfoDTO;
import ISA.Team54.users.mappers.UserInfoMapper;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.User;
import ISA.Team54.users.service.interfaces.PatientService;
import ISA.Team54.users.service.interfaces.UserService;

@RestController 
@RequestMapping(value="/examination",produces=MediaType.APPLICATION_JSON_VALUE)
public class ExaminationController {

	@Autowired
	private ExaminationService examinationService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private DrugService drugService;

	@Autowired
	private UserService userSerivce;


	@Autowired
	private EmailService emailService;

	@GetMapping("/soonestExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<StartExaminationDTO> loadSoonestExamination() {
		try {
			Examination soonestExamination = examinationService.getCurrentExaminationForEmployee();
	
			ExaminationDTO soonestExaminationDTO = new ExaminationMapper().ExaminationToExaminationDTO(soonestExamination);
			List<ExaminationDTO> historyExaminations = new ArrayList<ExaminationDTO>();
			long patientId = soonestExamination.getPatient().getId();
			for (Examination examination : examinationService.historyOfPatientExamination((long) patientId)) {
				User employee = userSerivce.findById(examination.getEmplyeedId());
				historyExaminations
						.add(new ExaminationMapper().ExaminationToExaminationDTOHistory(examination, employee));
			}
			List<DrugDTO> drugsForPatient = new ArrayList<DrugDTO>();
			for (Drug drug : drugService.getDrugsForPatient((long) patientId)) {
				drugsForPatient.add(new DrugMapper().DrugIntoDrugDTO(drug));
			}
			return new ResponseEntity<>(new StartExaminationDTO(soonestExaminationDTO, historyExaminations, drugsForPatient),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/isPatientAppropriate/{patientId}") 
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST', 'PATIENT')")
	public  ResponseEntity<String> IsPatientAppropriate(@PathVariable Long patientId) {
		int isPatientAppropriate = examinationService.isPatientAppropriate(patientId);
		if(isPatientAppropriate==1)
			 return new ResponseEntity<>("Izabrali ste odgovarajuceg pacijenta!",HttpStatus.OK);
		else if(isPatientAppropriate==0) {
			 return new ResponseEntity<>("Izabrani pacijent nema trenutno zakazano!",HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Izabrani pacijent nema trenutno zakazano!",HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/examinationHistory/{patientId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST', 'PATIENT')")
	public List<ExaminationDTO> examinationHistory(@PathVariable int patientId) {
		
		List<ExaminationDTO> historyExaminations = new ArrayList<ExaminationDTO>();

		try {
			for (Examination examination : examinationService.historyOfPatientExamination((long) patientId)) {
				historyExaminations.add(new ExaminationMapper().ExaminationToExaminationDTOHistory(examination, null));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return historyExaminations;
	}

	@PostMapping("/examination-history")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<EmployeeExaminationDTO>> getPatientExaminationsByType(@RequestBody ExaminationTypeDTO type){
		try {
			List<Examination> examinations = examinationService.getPatientExaminationsByType(type.getType());
			List<EmployeeExaminationDTO> examinationDTOS = new ArrayList<>();
			for (Examination examination : examinations) {
				User employee = userSerivce.findById(examination.getEmplyeedId());
				examinationDTOS.add(new ExaminationMapper().ExaminationToEmployeeExaminationDTO(examination, employee, type.getType()));
			}
			return new ResponseEntity<List<EmployeeExaminationDTO>>(examinationDTOS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/definedExaminations/{examinationId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public List<DefinedExaminationDTO> getDefinedExaminations(@PathVariable Long examinationId){
		List<DefinedExaminationDTO> definedExaminations = new ArrayList<DefinedExaminationDTO>();

		for (Examination examination : examinationService.getDefinedExaminations()) {
			definedExaminations.add(new DefinedExamiantionMapper().examinationToDefinedExaminationDTO(examination));
		}
		return definedExaminations;
	}

	@GetMapping("/schedule/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<String> scheduleExamination(@PathVariable long id) {
		try {
			examinationService.scheduleExamination(id);
			patientService.addLoyaltyPointsForScheduledExamination(id);
			return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } 		
	}

	@PostMapping("/scheduleExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
    public ResponseEntity<String> scheduleExamination(@RequestBody ScheduleExaminaitonDTO scheduleExamination) {
		try{
			examinationService.scheduleExamination(scheduleExamination.getDate());
			return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!",HttpStatus.OK);
		}catch (PessimisticLockingFailureException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    }
	 
	 @GetMapping("/examinaitonForCalendar/")
		@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
		public ResponseEntity<List<ExaminationForCalendarDTO>> getExaminaitonForCalendar() {
			try {
				List<ExaminationForCalendarDTO> examinationsForCalendar = examinationService.getExaminaitonForCalendar();
				return new ResponseEntity<>(examinationsForCalendar,HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}


	@GetMapping("/cancel/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<String> cancelExamination(@PathVariable long id) {
		try {
			examinationService.cancelExamination(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (InvalidTimeLeft e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@PostMapping("/future")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<EmployeeExaminationDTO>> getFutureExamination(@RequestBody ExaminationTypeDTO type) {
		try {
			List<EmployeeExaminationDTO> examinations = examinationService.getFutureExaminations(type.getType());
			return new ResponseEntity<List<EmployeeExaminationDTO>>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/updateExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<String> updateExamination(@RequestBody ExaminationInformationDTO examinationInformationDTO) {
		try {
		examinationService.updateExamination(examinationInformationDTO);
		return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Doslo je do greske, pokusajte ponovo!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/saveExamination/{newExaminationId}")
    @PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<String> saveExamination(@PathVariable Long newExaminationId) {
		boolean success = examinationService.saveExamination(newExaminationId);
		return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!", HttpStatus.OK);
	}  

	@GetMapping("/patient-employees")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<UserInfoDTO>> getPatientDermatologistFromExaminations(@RequestParam("type") ExaminationType type){
		List<User> employees = examinationService.getEmployeeWhoExaminedPatient(type);
		List<UserInfoDTO> employeeDTOs = new ArrayList<UserInfoDTO>();
		employees.forEach(e -> employeeDTOs.add(new UserInfoMapper().UserTOUserInfoDTO(e)));
		return new ResponseEntity<List<UserInfoDTO>>(employeeDTOs, HttpStatus.OK);
	}
}
