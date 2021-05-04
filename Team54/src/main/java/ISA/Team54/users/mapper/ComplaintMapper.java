package ISA.Team54.users.mapper;


import ISA.Team54.users.dto.ComplaintRequestDTO;
import ISA.Team54.users.model.Complaint;

public class ComplaintMapper {

	public static Complaint ComplaintDTOIntoComplaint(ComplaintRequestDTO complaintDTO) {
		return new Complaint(complaintDTO.getObjectId(),
							complaintDTO.getText(),
							complaintDTO.getType());
	}
}
