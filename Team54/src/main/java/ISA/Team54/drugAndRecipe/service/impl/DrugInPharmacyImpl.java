package ISA.Team54.drugAndRecipe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.mapper.DrugInPharmacyMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.exceptions.DrugReservedInFutureException;
import ISA.Team54.users.model.PharmacyAdministrator;
@Transactional(readOnly = true)
@Service
public class DrugInPharmacyImpl implements DrugInPharmacyService {
	
	@Autowired
	private DrugsInPharmacyRepository drugsInPharmacyRepository;
	@Autowired
	private DrugRepository drugRepository;
	@Autowired
	private DrugReservationRepository drugReservationRepository;

	
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
	@Transactional(readOnly = false, rollbackFor = DrugOutOfStockException.class )
	public void decreaseDrugQuantities(List<Long> drugIds, long pharmacyId, List<Integer> quantities) throws DrugOutOfStockException{
		List<DrugInPharmacy> drugsInPharmacy = drugsInPharmacyRepository.findAllByPharmacyId(pharmacyId);
		for (DrugInPharmacy drugInPharmacy : drugsInPharmacy) {
			for(int i = 0; i < drugIds.size(); i++){
				if(drugInPharmacy.getDrugInPharmacyId().getDrugId() == drugIds.get(i) && drugInPharmacy.getDrugInPharmacyId().getPharmaciId() == pharmacyId) {
					if(drugInPharmacy.getQuantity() < quantities.get(i))
						throw new DrugOutOfStockException();
					drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - quantities.get(i));
					drugsInPharmacyRepository.save(drugInPharmacy);
				}
			}
		}
	}

	@Transactional//(readOnly = false)
	@Override
	public void removeDrugFromPharmacy(long drugId, long pharmacyId) throws DrugReservedInFutureException {
		boolean isDrugReserved = !drugReservationRepository.getAllReservationsForDrugInPharmacyByStatus(drugId, pharmacyId, ReservationStatus.Reserved).isEmpty();

		if(isDrugReserved) 
			throw new DrugReservedInFutureException();
			
		DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findByDrugIdAndPharmacyId(drugId, pharmacyId);
		drugInPharmacy.setQuantity(-1);
		drugsInPharmacyRepository.save(drugInPharmacy); 
	}

	@Transactional
	@Override
	public void addDrugToPharmacy(DrugInPharmacy newDrugInPharmacy, boolean isInOrder) {
		DrugInPharmacy existingDrugInPharmacy = drugsInPharmacyRepository.findByDrugIdAndPharmacyId(newDrugInPharmacy.getDrugInPharmacyId().getDrugId(), newDrugInPharmacy.getDrugInPharmacyId().getPharmaciId());
		
		if(existingDrugInPharmacy == null) {
			if (isInOrder)
				newDrugInPharmacy.setQuantity(0);
			
			drugsInPharmacyRepository.save(newDrugInPharmacy);
		} else {
			if(existingDrugInPharmacy.getQuantity() == -1) {
				if (isInOrder)
					existingDrugInPharmacy.setQuantity(0);
				else
					existingDrugInPharmacy.setQuantity(newDrugInPharmacy.getQuantity());
			}
			else {
				if (!isInOrder)
					existingDrugInPharmacy.setQuantity(existingDrugInPharmacy.getQuantity() + newDrugInPharmacy.getQuantity());
			}
				
			drugsInPharmacyRepository.save(existingDrugInPharmacy);
		}
	}

	@Override
	public void updateDrugsQuantities(List<DrugInOrder> drugsInOrder) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long pharmacyId = ((PharmacyAdministrator) authentication.getPrincipal()).getPharmacy().getId();
		
		for (DrugInOrder drugInOrder : drugsInOrder) {
			System.out.println("GLEDAJ VAMO: " + drugInOrder.getId().getDrugId());
			DrugInPharmacy drugInPharmacy = drugsInPharmacyRepository.findByDrugIdAndPharmacyId(drugInOrder.getId().getDrugId(), pharmacyId);
			drugInPharmacy.setQuantity(drugInOrder.getQuantity());
			drugsInPharmacyRepository.save(drugInPharmacy);
		}
		
	} 
}
