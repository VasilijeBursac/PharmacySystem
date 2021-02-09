package ISA.Team54.Examination.mapper;

import ISA.Team54.Examination.dto.ExaminationForCalendarDTO;
import ISA.Team54.Examination.model.Examination;

public class ExaminationForCalendarMapper {

	public ExaminationForCalendarDTO examinationForExaminationForCalendarDTO(Examination examination) {
		return new ExaminationForCalendarDTO(examination.getId(),examination.getTerm().getStart(),
				examination.getTerm().getDuration(),examination.getPharmacy().getName(),
				examination.getPatient().getName(),examination.getPatient().getSurname());
	}
}
