package ISA.Team54.users.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.users.dto.PharmacistInfoDTO;
import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.exceptions.EmployeeHasScheduledExaminationsException;
import ISA.Team54.users.mapper.PharmacistMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.users.service.interfaces.PharmacistService;

@Service
public class PharmacistServiceImpl implements PharmacistService{
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private ExaminationService examinationService;
	
	
	
	public Pharmacist findOneById(long id) {
		return pharmacistRepository.findOneById(id);
	}
	
	@Override
	public List<Pharmacist> getAllPharmacists() {
		return pharmacistRepository.findAll();
	}
	
	@Override
	public List<Pharmacist> getAllPharmacistsInPharmacy(long pharmacyId) {
		return pharmacistRepository.findAllByPharmacyId(pharmacyId);
	}

	@Override
	public void removePharmacistFromPharmacy(long pharmacistId) throws Exception {
		if (examinationService.checkIfEmployeeHasScheduledExaminationsInFuture(pharmacistId))
			throw new EmployeeHasScheduledExaminationsException();
		
		Pharmacist pharmacist = pharmacistRepository.findOneById(pharmacistId);
		pharmacist.setPharmacy(null);
		pharmacistRepository.save(pharmacist);
	}
}
