package ISA.Team54.shared.dto;

import java.util.Date;

public class TimePeriodDTO {
	Date startDate;
	Date endDate;
	
	
	public TimePeriodDTO() {
		super();
	}

	public TimePeriodDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
