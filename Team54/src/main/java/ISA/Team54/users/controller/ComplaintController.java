package ISA.Team54.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.users.dto.ComplaintDTO;
import ISA.Team54.users.dto.ComplaintRequestDTO;
import ISA.Team54.users.dto.ComplaintRespondDTO;
import ISA.Team54.users.dto.PatientDTO;
import ISA.Team54.users.enums.ComplaintType;
import ISA.Team54.users.mapper.ComplaintMapper;
import ISA.Team54.users.mapper.PatientMapper;
import ISA.Team54.users.model.Complaint;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.model.User;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.PharmacyRepository;
import ISA.Team54.users.repository.UserRepository;
import ISA.Team54.users.service.interfaces.ComplaintService;
import ISA.Team54.users.service.interfaces.PatientService;

@RestController
@RequestMapping(value = "/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {
				
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@PostMapping("/addComplaint")
	@PreAuthorize("hasRole('PATIENT')")
	public  ResponseEntity<String> addComplaint(@RequestBody ComplaintRequestDTO complaintDTO){
		
		try {
			Complaint complaint = ComplaintMapper.ComplaintDTOIntoComplaint(complaintDTO);
			complaintService.addComplaint(complaint);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch ( PessimisticLockingFailureException e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}	
	} 
	
	@PostMapping("/respondToComplaint")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public  ResponseEntity<String> respondToComplaint(@RequestBody ComplaintRespondDTO complaintRespondDTO){
		try {
			complaintService.respondToComplaint(complaintRespondDTO.getId(), 
												complaintRespondDTO.getResponse(),
												complaintRespondDTO.getEmail(), 
												complaintRespondDTO.getComplaintType());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	} 
	
	@GetMapping("/allComplaints")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<List<ComplaintDTO>> findAllComplaints(){
		List<ComplaintDTO> complaintDTOs = new ArrayList<ComplaintDTO>();
		try {
			complaintService.findAllComplaints().forEach(complaint -> complaintDTOs.add(
					ComplaintMapper.ComplaintIntoComplaintDTO(complaint, getComplaintObjectName(complaint))));
			return new ResponseEntity<>(complaintDTOs, HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
	}
	
	private String getComplaintObjectName(Complaint complaint) {
		if(complaint.getType().equals(ComplaintType.PharmacyComplaint)) 
			return pharmacyRepository.findById(complaint.getObjectId()).getName();
		return userRepository.findOneById((long)complaint.getObjectId()).getName() + " "
			   + userRepository.findOneById((long)complaint.getObjectId()).getSurname();
		
	}
	
	
}
