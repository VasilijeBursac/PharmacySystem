package ISA.Team54.vacationAndWorkingTime.mappers;

import ISA.Team54.vacationAndWorkingTime.dto.VacationRequestDTO;
import ISA.Team54.vacationAndWorkingTime.model.VacationRequest;


public class VacationRequestMapper {
	public static VacationRequestDTO VacationRequestToVacationRequestDTO(VacationRequest vacationRequest) {
		VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
		
		vacationRequestDTO.setId(vacationRequest.getId());
		
		if(vacationRequest.getPharmacist() != null) {
			vacationRequestDTO.setName(vacationRequest.getPharmacist().getName());
			vacationRequestDTO.setSurname(vacationRequest.getPharmacist().getSurname());
		} else {
			vacationRequestDTO.setName(vacationRequest.getDermatologist().getName());
			vacationRequestDTO.setSurname(vacationRequest.getDermatologist().getSurname());
		}
		
		vacationRequestDTO.setStatus(vacationRequest.getStatus());
		vacationRequestDTO.setTimePeriod(vacationRequest.getTimePeriod());
		
		return vacationRequestDTO;
	}
}
