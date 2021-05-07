package ISA.Team54.users.mapper;


import ISA.Team54.users.dto.ComplaintDTO;
import ISA.Team54.users.dto.ComplaintRequestDTO;
import ISA.Team54.users.model.Complaint;

public class ComplaintMapper {

	public static Complaint ComplaintDTOIntoComplaint(ComplaintRequestDTO complaintDTO) {
		return new Complaint(complaintDTO.getObjectId(),
							complaintDTO.getText(),
							complaintDTO.getType());
	}
	
	public static ComplaintDTO ComplaintIntoComplaintDTO(Complaint complaint,String complaintObjectName) {
		return new ComplaintDTO(complaint.getPatient().getName() + " "
				+ complaint.getPatient().getSurname(),
				complaintObjectName,
				complaint.getText(),
				complaint.getPatient().getEmail(), 
				complaint.getType());
	}
}
