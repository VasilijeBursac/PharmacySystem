package ISA.Team54.users.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.enums.ComplaintType;
import ISA.Team54.users.model.Complaint;
import ISA.Team54.users.repository.ComplaintRepository;
import ISA.Team54.users.repository.UserRepository;
import ISA.Team54.users.service.interfaces.ComplaintService;
import ISA.Team54.users.service.interfaces.UserService;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Complaint addComplaint(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public void respondToComplaint(String respond, String email, ComplaintType complaintType) {
		emailService.sendEmail(email,"Zalba na " + getNameOfComplaintType(complaintType),respond);		
	}

	private String getNameOfComplaintType(ComplaintType complaintType) {
		switch(complaintType) {
			case PharmacyComplaint:
				return "apoteku";
			case PharmacistComplaint:
				return "farmaceuta";				
			default:
				return "dermatologa";
		}
	}

	@Override
	public List<Complaint> findAllComplaints() {
		return complaintRepository.findAll();
	}
	
}