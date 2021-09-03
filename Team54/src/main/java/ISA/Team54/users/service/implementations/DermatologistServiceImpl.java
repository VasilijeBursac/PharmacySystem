package ISA.Team54.users.service.implementations;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.shared.model.DateRange;
import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.dto.DermatologistToPharmacyDTO;
import ISA.Team54.users.exceptions.EmployeeHasScheduledExaminationsException;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PharmacyRepository;
import ISA.Team54.users.service.interfaces.DermatologistService;
import ISA.Team54.users.service.interfaces.PharmacyService;
import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;
import ISA.Team54.vacationAndWorkingTime.service.interfaces.DermatologistWorkScheduleService;

@Service
public class DermatologistServiceImpl implements DermatologistService {
	
	@Autowired
	DermatologistRepository dermatologistRepository;
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	@Autowired 
	ExaminationService examinationService;
	
	@Autowired 
	DermatologistWorkScheduleService dermatologistWorkScheduleService;
	
	
	@Override
	public List<Dermatologist> findAll() {
		if(dermatologistRepository.findAll().size() != 0)
			return dermatologistRepository.findAll();
		return null;
	}

	@Override
	public Dermatologist findOneById(Long id) {
		return dermatologistRepository.findOneById(id);
	}

	@Override
	public List<Dermatologist> getAllDermatologists() {
		return dermatologistRepository.findAll();
	}	
	
	@Override
	public List<Dermatologist> getAllDermatologistsInPharmacy(long pharmacyId) {
		return pharmacyRepository.findById(pharmacyId).getDermatologists();
	}
	
	@Override
	public List<Dermatologist> getAllDermatologistsNotInPharmacy(long pharmacyId) {
		List<Dermatologist> allDermatologists = getAllDermatologists();
		List<Dermatologist> dermatologistsInPharmacy = getAllDermatologistsInPharmacy(pharmacyId);
		
		List<Dermatologist> dermatologistsNotInPharmacy = new ArrayList<>(allDermatologists);
		dermatologistsNotInPharmacy.removeAll(dermatologistsInPharmacy);
		
		return dermatologistsNotInPharmacy;
	}

	@Override
	public void removeDermatologistFromPharmacy(long dermatologistId, long pharmacyId) throws Exception {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId);
		
		if (examinationService.checkIfEmployeeHasScheduledExaminationsInFuture(dermatologistId, pharmacyId))
			throw new EmployeeHasScheduledExaminationsException();
		
		List<Dermatologist> pharmacyDermatologists = pharmacy.getDermatologists();
		
//		dermatologistWorkScheduleService.deleteByDermatologistAndPharmacy(dermatologistId, pharmacyId);
		
		Iterator<Dermatologist> it = pharmacyDermatologists.iterator();
		while (it.hasNext()) {
			Dermatologist dermatologist = it.next();
			if(dermatologist.getId() == dermatologistId) {
				it.remove();
				break;				
			}
		}
		pharmacyRepository.save(pharmacy);
		
	}

	@Override
	public void addDermatologistToPharmacy(DermatologistToPharmacyDTO dermatologistToPharmacyDTO, long pharmacyId) {
		long dermatologistId = dermatologistToPharmacyDTO.getDermatologistId();
		
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId);
		Dermatologist dermatologist = dermatologistRepository.findOneById(dermatologistId);
		
		DermatologistWorkSchedule workSchedule = dermatologistWorkScheduleService.getDermatologistWorkScheduleInPharmacy(dermatologistId, pharmacyId);
//		if (workSchedule != null)
//			workSchedule.setTimePeriod(new DateRange(dermatologistToPharmacyDTO.getStartDate(), dermatologistToPharmacyDTO.getEndDate()));
//		else
			dermatologist.getWorkSchedule().add(new DermatologistWorkSchedule(new DateRange(dermatologistToPharmacyDTO.getStartDate(), dermatologistToPharmacyDTO.getEndDate()), dermatologist, pharmacy));
		
//		List<DermatologistWorkSchedule> dermatologistWorkSchedules = dermatologist.getWorkSchedule();
		
		dermatologistRepository.save(dermatologist);
		pharmacy.getDermatologists().add(dermatologist);
		pharmacyRepository.save(pharmacy);
	}
}
