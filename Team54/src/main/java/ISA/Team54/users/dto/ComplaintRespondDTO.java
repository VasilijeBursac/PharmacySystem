package ISA.Team54.users.dto;

import ISA.Team54.users.enums.ComplaintType;

public class ComplaintRespondDTO {
	
	private long id;
	private String response;
	private String email;
	private ComplaintType complaintType;
	
	public ComplaintRespondDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComplaintRespondDTO(long id,String response, String email, ComplaintType complaintType) {
		super();
		this.id = id;
		this.response = response;
		this.email = email;
		this.complaintType = complaintType;

	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ComplaintType getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(ComplaintType complaintType) {
		this.complaintType = complaintType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
