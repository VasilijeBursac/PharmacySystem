package ISA.Team54.promotion.service.implementations;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ISA.Team54.promotion.dto.PromotionDTO;
import ISA.Team54.promotion.model.Promotion;
import ISA.Team54.promotion.repository.PromotionRepository;
import ISA.Team54.promotion.service.interfaces.PromotionService;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Patient;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
	private PromotionRepository promotionRepository;
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<Promotion> getAllPromotionsForPharmacy(long pharmacyId) {
		return promotionRepository.findAllByPharmacyId(pharmacyId);
	}

	@Override
	public void addNewPromotion(Promotion promotion) {
		SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("dd/MM/yyyy"); 
		List<Patient> subscribedPatients = promotion.getPharmacy().getSubscribedPatients();
		
		for(Patient patient : subscribedPatients) {
			new Thread(() -> {
				emailService.sendEmail(patient.getEmail(), 
						"Nova promocija u apoteci " + promotion.getPharmacy().getName() , 
						"Dodata je nova promocija u apoteku " + promotion.getPharmacy().getName() 
						+ ". Opis promocije: " + promotion.getDescription() 
						+ ". Period važenja promocije: Od " + simpleDateFormat .format(promotion.getDateInterval().getStartDate()) + " do " + simpleDateFormat .format(promotion.getDateInterval().getEndDate()));
			}).start();
		}
		
		promotionRepository.save(promotion);	
	}

	@Override
	public void deletePromotionById(long promotionId) {
		promotionRepository.deleteById(promotionId);
	}
	
	
}
