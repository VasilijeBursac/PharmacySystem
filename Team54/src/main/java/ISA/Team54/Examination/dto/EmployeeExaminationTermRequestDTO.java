package ISA.Team54.Examination.dto;

import java.util.Date;

public class EmployeeExaminationTermRequestDTO {
	private Date termStart;
	private int duration;
	private long employeeId;
	private int price;
	
	
	public EmployeeExaminationTermRequestDTO() {
		super();
	}
	
	public EmployeeExaminationTermRequestDTO(Date termStart, int duration, long employeeId, int price) {
		super();
		this.termStart = termStart;
		this.duration = duration;
		this.employeeId = employeeId;
		this.price = price;
	}

	

	public Date getTermStart() {
		return termStart;
	}

	public void setTermStart(Date termStart) {
		this.termStart = termStart;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
