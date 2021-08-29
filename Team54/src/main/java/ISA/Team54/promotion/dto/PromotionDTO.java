package ISA.Team54.promotion.dto;

import ISA.Team54.shared.model.DateRange;

public class PromotionDTO {
	private long id;
	private String description;
	private DateRange dateInterval;
	private long pharmacyId;
	
	
	public PromotionDTO() {
		super();
	}
	
	public PromotionDTO(long id, String description, DateRange dateInterval, long pharmacyId) {
		super();
		this.id = id;
		this.description = description;
		this.dateInterval = dateInterval;
		this.pharmacyId = pharmacyId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateRange getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(DateRange dateInterval) {
		this.dateInterval = dateInterval;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
	
}

