package ISA.Team54.Examination.dto;


import java.util.Date;

import ISA.Team54.Examination.model.Term;

public class EmployeeExaminationDTO {
	private long examinationId;
	private Date term;
	private String employee;
	private long employeeId;
	private double employeeRating;
	private double price;
	
	public EmployeeExaminationDTO(long examinationId, Term term, String employee, long employeeId, double employeeRating,
			double price) {
		super();
		this.examinationId = examinationId;
		this.term = term.getStart();
		this.employee = employee;
		this.employeeId = employeeId;
		this.employeeRating = employeeRating;
		this.price = price;
	}

	public EmployeeExaminationDTO() {
		super();
	}

	public long getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(long examinationId) {
		this.examinationId = examinationId;
	}

	public Date getTerm() {
		return term;
	}

	public void setTerm(Date term) {
		this.term = term;
	}

	public String getEmployee() {
		return this.employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public double getEmployeeRating() {
		return employeeRating;
	}

	public void setEmployeeRating(double employeeRating) {
		this.employeeRating = employeeRating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	
	
}
