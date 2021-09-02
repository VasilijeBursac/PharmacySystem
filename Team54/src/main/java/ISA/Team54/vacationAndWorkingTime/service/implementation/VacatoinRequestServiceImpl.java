package ISA.Team54.vacationAndWorkingTime.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.shared.model.DateRange;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.vacationAndWorkingTime.enums.VacationRequestStatus;
import ISA.Team54.vacationAndWorkingTime.model.VacationRequest;
import ISA.Team54.vacationAndWorkingTime.repository.VacationRequestRepository;
import ISA.Team54.vacationAndWorkingTime.service.interfaces.VacationRequestService;

@Service
public class VacatoinRequestServiceImpl implements VacationRequestService {

	@Autowired
	private VacationRequestRepository vacationRequestRepository;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private DermatologistRepository dermatologistRepository;
	@Autowired 
	private EmailService emailService;
	
	
	@Override
	public void scheduleVacation(DateRange dateRange) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneById(((Pharmacist) authentication.getPrincipal()).getId());
			if(pharmacist !=null) 
				vacationRequestRepository.save(new VacationRequest(dateRange,VacationRequestStatus.Created,null,pharmacist));
		}catch(Exception e) {
			Dermatologist dermatologist = dermatologistRepository.findOneById(((Dermatologist) authentication.getPrincipal()).getId());
			if(dermatologist !=null)
				vacationRequestRepository.save(new VacationRequest(dateRange,VacationRequestStatus.Created,dermatologist,null));
		}
	}
	
	@Override
	public List<VacationRequest> getAllPharmacistsVacationRequestsForPharmacy(long pharmacyId) {
		return vacationRequestRepository.getPharmacistsVacationRequestsByPharmacyId(pharmacyId);
	}
	
	@Override
	public List<VacationRequest> getAllDermatologistsVacationRequests() {
		return vacationRequestRepository.getDermatologistsVacationRequests();
	}
	
	@Override
	public void respondToVacationRequest(long vacationRequestId, boolean isApproved, String responseMessage) {
		VacationRequest vacationRequest = vacationRequestRepository.findOneById(vacationRequestId);
		
		String employeeEmail;
		String emailMessage;
		
		if (isApproved) {
			vacationRequest.setStatus(VacationRequestStatus.Approved);
			emailMessage = "Vaš zahtev za odsustvo ili godišnji odmor je prihvaćen.";
		} else {
			vacationRequest.setStatus(VacationRequestStatus.Rejected);
			vacationRequest.setResponseMessage(responseMessage);
			emailMessage = "Vaš zahtev za odsustvo ili godišnji odmor je odbijen." 
							+ " Razlog odbijanja: " + responseMessage;
		}
		
		if(vacationRequest.getPharmacist() != null)
			employeeEmail = vacationRequest.getPharmacist().getEmail();
		else
			employeeEmail = vacationRequest.getDermatologist().getEmail();
		
		
		new Thread(() -> {
			emailService.sendEmail(employeeEmail, "Obrađen zahtev za odsustvo", emailMessage);
		}).start();
		
		vacationRequestRepository.save(vacationRequest);
	}
}
