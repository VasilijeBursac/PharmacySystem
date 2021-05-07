package ISA.Team54.loyalty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.loyalty.enums.LoyaltyExaminationPoints;
import ISA.Team54.loyalty.model.ExaminationLoyaltyPoints;

public interface ExamiantionLoyaltyPointsRepository extends JpaRepository<ExaminationLoyaltyPoints, Long>{
	
	ExaminationLoyaltyPoints findByType (LoyaltyExaminationPoints type);
}
