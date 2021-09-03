package ISA.Team54.vacationAndWorkingTime.dto;

import ISA.Team54.shared.model.DateRange;
import ISA.Team54.vacationAndWorkingTime.enums.VacationRequestStatus;

public class VacationRequestDTO {
	private long id;
	private String name;
	private String surname;
	private VacationRequestStatus status;
	private DateRange timePeriod;
	
	
	public VacationRequestDTO() {
		super();
	}


	public VacationRequestDTO(long id, String name, String surname, VacationRequestStatus status,
			DateRange timePeriod) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.status = status;
		this.timePeriod = timePeriod;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public VacationRequestStatus getStatus() {
		return status;
	}


	public void setStatus(VacationRequestStatus status) {
		this.status = status;
	}


	public DateRange getTimePeriod() {
		return timePeriod;
	}


	public void setTimePeriod(DateRange timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	
}
