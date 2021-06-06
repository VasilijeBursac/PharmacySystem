package ISA.Team54.loyalty.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import ISA.Team54.loyalty.enums.LoyaltyExaminationPoints;

@Entity
public class ExaminationLoyaltyPoints {
	
	@Id
	@Enumerated(EnumType.STRING)
	private LoyaltyExaminationPoints type;
	
	@Column(nullable = false)
	private int points;
	
	public ExaminationLoyaltyPoints(LoyaltyExaminationPoints type, int points) {
		this.type = type;
		this.points = points;
	}

	public ExaminationLoyaltyPoints() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoyaltyExaminationPoints getType() {
		return type;
	}

	public void setType(LoyaltyExaminationPoints type) {
		this.type = type;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}	
	
	
}
