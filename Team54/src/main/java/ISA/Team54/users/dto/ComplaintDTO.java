package ISA.Team54.users.dto;

import ISA.Team54.users.enums.ComplaintType;

public class ComplaintDTO {
	private String patient;
	private String complaintObjectName;
	private String text;
	private String email;
	private ComplaintType complaintType;
		 
	public ComplaintDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplaintDTO(String patient, String complaintObjectName, String text, String email, ComplaintType complaintType) {
		super();
		this.patient = patient;
		this.complaintObjectName = complaintObjectName;
		this.text = text;
		this.email = email;
		this.complaintType = complaintType;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getComplaintObjectName() {
		return complaintObjectName;
	}

	public void setComplaintObjectName(String complaintObjectName) {
		this.complaintObjectName = complaintObjectName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ComplaintType getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(ComplaintType complaintType) {
		this.complaintType = complaintType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
