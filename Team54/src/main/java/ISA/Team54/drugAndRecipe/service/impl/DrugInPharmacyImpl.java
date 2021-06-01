package ISA.Team54.drugAndRecipe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
@Transactional(readOnly = true)
@Service
public class DrugInPharmacyImpl implements DrugInPharmacyService {
	
	@Autowired
	private DrugsInPharmacyRepository drugsInPharmacyRepository;
	@Autowired
	private DrugRepository drugRepository;

	
	@Override
	public List<DrugInPharmacy> getDrugsInPharmaciesByDrug(long id) {
		if(drugsInPharmacyRepository.findByDrugId(id).size() != 0)
			return drugsInPharmacyRepository.findByDrugId(id);
		return null;
	}

	@Override
	public List<DrugInPharmacyDTO> getAllDrugsInPharmacy(long pharmacyId) {
		List<DrugInPharmacyDTO> drugsInPharmacyDTOs = new ArrayList<DrugInPharmacyDTO>();
		List<DrugInPharmacy> drugsInPharmacy = drugsInPharmacyRepository.findAllByDrugInPharmacyIdPharmaciId(pharmacyId);
		
		for(DrugInPharmacy drugInPharmacy : drugsInPharmacy) {
			Drug drug = drugRepository.findOneById(drugInPharmacy.getDrugInPharmacyId().getDrugId());
			DrugInPharmacyDTO drugInPharmacyDTO = DrugInPharmacyMapper.DrugInPharmacyToDrugInPharmacyDTO(drugInPharmacy, drug);
			drugsInPharmacyDTOs.add(drugInPharmacyDTO);
		}
		return drugsInPharmacyDTOs;
	}

	@Override
	public List<DrugInPharmacy> getAllDrugsInPharmacy() {
		if(drugsInPharmacyRepository.findAll().size() == 0)
			return null;
		return drugsInPharmacyRepository.findAll();
	}

	@Override
	public List<DrugInPharmacy> getDrugsInPharmaciesByPharmacy(long pharmacyId) {
		if(drugsInPharmacyRepository.findAllByDrugInPharmacyIdPharmaciId(pharmacyId).size() != 0)
			return drugsInPharmacyRepository.findAllByDrugInPharmacyIdPharmaciId(pharmacyId);
		return null;
	}

	
	@Override
	@Transactional(readOnly = false)
	public void decreaseDrugQuantities(List<Long> drugIds, long pharmacyId, List<Integer> quantities) throws PessimisticLockingFailureException{
		List<DrugInPharmacy> drugsInPharmacy = drugsInPharmacyRepository.findAll();
		for (DrugInPharmacy drugInPharmacy : drugsInPharmacy) {
			for(int i = 0; i < drugIds.size(); i++){
				if(drugInPharmacy.getDrugInPharmacyId().getDrugId() == drugIds.get(i) && drugInPharmacy.getDrugInPharmacyId().getPharmaciId() == pharmacyId) {
					drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - quantities.get(i));
					drugsInPharmacyRepository.save(drugInPharmacy);
				}
			}
		}
		
	}
}
