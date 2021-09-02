package ISA.Team54.vacationAndWorkingTime.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.shared.model.DateRange;
import ISA.Team54.users.dto.DermatologistInfoDTO;
import ISA.Team54.users.mapper.DermatologistMapper;
import ISA.Team54.vacationAndWorkingTime.dto.VacationRequestDTO;
import ISA.Team54.vacationAndWorkingTime.dto.VacationRequestResponseDTO;
import ISA.Team54.vacationAndWorkingTime.mappers.VacationRequestMapper;
import ISA.Team54.vacationAndWorkingTime.service.interfaces.VacationRequestService;

@RestController
@RequestMapping(value = "/vacation", produces = MediaType.APPLICATION_JSON_VALUE)
public class VacationRequestController {
	@Autowired
	private VacationRequestService vacationRequestService;

	@PostMapping("/scheduleVacation")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PHARMACIST')")
	public ResponseEntity<String> scheduleVacation(@RequestBody DateRange scheduleVacation) {
		try {
			vacationRequestService.scheduleVacation(scheduleVacation);
			return new ResponseEntity<>("Uspesno poslat zahtev!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Zahtev trenutno nije moguce poslati!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequestsForPharmacy(@PathVariable Long pharmacyId){
		try {
			List<VacationRequestDTO> vacationRequestsDTOs = new ArrayList<VacationRequestDTO>();
			vacationRequestService.getAllVacationRequestsForPharmacy(pharmacyId).forEach(vacationRequest -> 
			vacationRequestsDTOs.add(VacationRequestMapper.VacationRequestToVacationRequestDTO(vacationRequest)));
			return new ResponseEntity<>(vacationRequestsDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/respond/{vacationRequestId}")
	@PreAuthorize("hasRole('ROLE_PHARMACY_ADMIN')")
	public ResponseEntity<String> respondToVacationRequest(@RequestBody VacationRequestResponseDTO vacationRequestResponseDTO, @PathVariable Long vacationRequestId){
		try {
			vacationRequestService.respondToVacationRequest(vacationRequestId, vacationRequestResponseDTO.isApproved(), vacationRequestResponseDTO.getResponseMessage());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
