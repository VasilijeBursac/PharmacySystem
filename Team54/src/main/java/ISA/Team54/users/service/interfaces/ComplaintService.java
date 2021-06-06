package ISA.Team54.users.service.interfaces;

import java.util.List;

import org.springframework.dao.PessimisticLockingFailureException;

import ISA.Team54.users.enums.ComplaintType;
import ISA.Team54.users.model.Complaint;

public interface ComplaintService {
	Complaint addComplaint(Complaint complaint);
	void respondToComplaint(long id, String respond, String gmail, ComplaintType complaintType ) throws PessimisticLockingFailureException;
	List<Complaint> findAllComplaints();
}
