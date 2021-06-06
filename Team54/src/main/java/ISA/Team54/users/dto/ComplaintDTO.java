package ISA.Team54.users.dto;

import ISA.Team54.users.enums.ComplaintType;

public class ComplaintDTO {
	private long id;
	private String patient;
	private String complaintObjectName;
	private String text;
	private String email;
	private boolean responded;
	private ComplaintType complaintType;
		 
	public ComplaintDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplaintDTO(long id, String patient, String complaintObjectName, String text, String email,boolean responded, ComplaintType complaintType) {
		super();
		this.id = id;
		this.patient = patient;
		this.complaintObjectName = complaintObjectName;
		this.text = text;
		this.email = email;
		this.responded = responded;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}
	
	
}
