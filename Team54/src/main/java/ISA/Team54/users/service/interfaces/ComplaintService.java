package ISA.Team54.users.service.interfaces;

import java.util.List;

import ISA.Team54.users.enums.ComplaintType;
import ISA.Team54.users.model.Complaint;

public interface ComplaintService {
	Complaint addComplaint(Complaint complaint);
	void respondToComplaint(String respond, String gmail, ComplaintType complaintType );
	List<Complaint> findAllComplaints();
}
