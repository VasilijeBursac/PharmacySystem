package ISA.Team54.Examination.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ISA.Team54.Examination.dto.DermatologistExaminationDTO;
import ISA.Team54.exceptions.InvalidTimeLeft;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.dto.DefinedExaminationDTO;
import ISA.Team54.Examination.dto.DermatologistExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationDTO;
import ISA.Team54.Examination.dto.ExaminationForCalendarDTO;
import ISA.Team54.Examination.dto.ExaminationInformationDTO;
import ISA.Team54.Examination.dto.ExaminationTypeDTO;
import ISA.Team54.Examination.dto.NewExaminationDTO;
import ISA.Team54.Examination.dto.ScheduleExaminaitonDTO;
import ISA.Team54.Examination.dto.StartExaminationDTO;
import ISA.Team54.Examination.mapper.DefinedExamiantionMapper;
import ISA.Team54.Examination.mapper.ExaminationMapper;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.service.interfaces.DermatologistService;
import ISA.Team54.users.service.interfaces.PatientService;

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
	private DermatologistService dermatologistSerivce;
	@Autowired
	private EmailService emailService;
	@GetMapping("/soonestExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public StartExaminationDTO loadSoonestExamination() {
		Examination soonestExamination = examinationService.getCurrentExaminationByDermatologistId();

		ExaminationDTO soonestExaminationDTO = new ExaminationMapper().ExaminationToExaminationDTO(soonestExamination);
		List<ExaminationDTO> historyExaminations = new ArrayList<ExaminationDTO>();
		long patientId = soonestExamination.getPatient().getId();
		for (Examination examination : examinationService.historyOfPatientExamination((long) patientId)) {
			Dermatologist dermatologist = dermatologistSerivce.findOneById((long) examination.getEmplyeedId());
			historyExaminations
					.add(new ExaminationMapper().ExaminationToExaminationDTOHistory(examination, dermatologist));
		}
		List<DrugDTO> drugsForPatient = new ArrayList<DrugDTO>();
		List<Drug> eee = drugService.getDrugsForPatient((long) patientId);
		for (Drug drug : drugService.getDrugsForPatient((long) patientId)) {
			drugsForPatient.add(new DrugMapper().DrugIntoDrugDTO(drug));
		}
		return new StartExaminationDTO(soonestExaminationDTO, historyExaminations, drugsForPatient);
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

		for (Examination examination : examinationService.historyOfPatientExamination((long) patientId)) {
			historyExaminations.add(new ExaminationMapper().ExaminationToExaminationDTOHistory(examination, null));
		}
		return historyExaminations;
	}

	@GetMapping("/definedExaminations/{examinationId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public List<DefinedExaminationDTO> getDefinedExaminations(@PathVariable Long examinationId){
		List<DefinedExaminationDTO> definedExaminations = new ArrayList<DefinedExaminationDTO>();

		for (Examination examination : examinationService.getDefinedExaminations(examinationId)) {
			definedExaminations.add(new DefinedExamiantionMapper().examinationToDefinedExaminationDTO(examination));
		}
		return definedExaminations;
	}

	@GetMapping("/schedule/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public void scheduleExamination(@PathVariable long id) {
		examinationService.scheduleExamination(id); 
	}

	 @PostMapping("/scheduleExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
    public ResponseEntity<String> scheduleExamination(@RequestBody ScheduleExaminaitonDTO scheduleExamination) {	
      if(examinationService.scheduleExamination(scheduleExamination.getExaminationId(),scheduleExamination.getDate()))
    	  return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!",HttpStatus.OK);
      else
    	  return new ResponseEntity<>("Nije moguce zakazati pregled u izabranom terminu!",HttpStatus.BAD_REQUEST);  
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
	public ResponseEntity<List<DermatologistExaminationDTO>> getFutureExamination(@RequestBody ExaminationTypeDTO type) {
		try {
			List<DermatologistExaminationDTO> examinations = examinationService.getFutureExaminations(type.getType());
			return new ResponseEntity<List<DermatologistExaminationDTO>>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/updateExamination")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<String> updateExamination(@RequestBody ExaminationInformationDTO examinationInformationDTO) {
		examinationService.updateExamination(examinationInformationDTO);
		return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!", HttpStatus.OK);
	}
	
	@PostMapping("/saveExamination")
    @PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<String> saveExamination(@RequestBody NewExaminationDTO newExaminationDTO) {
		boolean success = examinationService.saveExamination(newExaminationDTO.getCurrentExaminationId(),newExaminationDTO.getNewExaminationId());
		return new ResponseEntity<>("Uspjesno sacuvane infomracije o pregledu!", HttpStatus.OK);
	}
	
}
