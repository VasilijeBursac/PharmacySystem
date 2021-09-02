package ISA.Team54.vacationAndWorkingTime.dto;

public class VacationRequestResponseDTO {
	private boolean isApproved;
	private String responseMessage;
	
	
	public VacationRequestResponseDTO() {
		super();
	}

	public VacationRequestResponseDTO(boolean isApproved, String responseMessage) {
		super();
		this.isApproved = isApproved;
		this.responseMessage = responseMessage;
	}

	
	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
}
