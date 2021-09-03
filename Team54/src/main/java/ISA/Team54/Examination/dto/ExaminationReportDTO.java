package ISA.Team54.Examination.dto;

import java.util.Date;

public class ExaminationReportDTO {
	Date date;
	

	public ExaminationReportDTO() {
		super();
	}
	
	public ExaminationReportDTO(Date date) {
		super();
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
