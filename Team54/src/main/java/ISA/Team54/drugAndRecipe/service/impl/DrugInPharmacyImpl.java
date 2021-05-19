package ISA.Team54.drugAndRecipe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;

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
}
