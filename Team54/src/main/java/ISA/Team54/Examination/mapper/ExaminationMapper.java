package ISA.Team54.Examination.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import ISA.Team54.Examination.dto.EmployeeExaminationDTO;
import ISA.Team54.Examination.dto.EmployeeExaminationTermRequestDTO;
import ISA.Team54.Examination.dto.ExaminationDTO;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.model.Term;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.User;
import ISA.Team54.users.service.interfaces.DermatologistService;
import ISA.Team54.users.service.interfaces.UserService;

public class ExaminationMapper {

	@Autowired
	private DermatologistService dermatologsitService;
	@Autowired
	private UserService userService;
	
	public ExaminationDTO ExaminationToExaminationDTO(Examination examination) {
		return new ExaminationDTO(examination.getId(),examination.getPatient().getName(),examination.getPatient().getSurname(),examination.getTerm().getStart());
	}
	public EmployeeExaminationDTO ExaminationToEmployeeExaminationDTO(Examination examination, User user, ExaminationType type) {
		double rating;
		if(type.equals(ExaminationType.DermatologistExamination))
			rating = ((Dermatologist)user).getRatings();
		else rating = ((Pharmacist)user).getRatings();
		
		return new EmployeeExaminationDTO(
				examination.getId(), 
				examination.getTerm(), 
				user.getName() + " " + user.getSurname(),
				user.getId(),
				rating,
				examination.getPrice());
	}
	public ExaminationDTO ExaminationToExaminationDTOHistory(Examination examination,User user) {
		if(user != null) 
		{
			return new ExaminationDTO(examination.getId(),user.getName(),user.getSurname(),examination.getTerm().getStart());
			
		}
		return null;
	}

	public static Examination EmployeeExaminationTermRequestToExamination(EmployeeExaminationTermRequestDTO employeeExaminationTermRequestDTO) {
		return new Examination (employeeExaminationTermRequestDTO.getEmployeeId(),
								new Term(employeeExaminationTermRequestDTO.getTermStart(), employeeExaminationTermRequestDTO.getDuration()),
								employeeExaminationTermRequestDTO.getPrice());
	}
}
