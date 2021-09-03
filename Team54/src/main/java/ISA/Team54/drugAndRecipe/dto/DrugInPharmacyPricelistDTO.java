package ISA.Team54.drugAndRecipe.dto;

import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.shared.model.DateRange;

public class DrugInPharmacyPricelistDTO {
	
	private DrugInPharmacyId drugInPharmacyId;
	private DateRange priceValidDateRange;
	private float price;
	
	
	public DrugInPharmacyPricelistDTO() {
		super();
	}

	public DrugInPharmacyPricelistDTO(DrugInPharmacyId drugInPharmacyId, DateRange priceValidDataRange,
			float price) {
		super();
		this.drugInPharmacyId = drugInPharmacyId;
		this.priceValidDateRange = priceValidDateRange;
		this.price = price;
	}

	public DrugInPharmacyId getDrugInPharmacyId() {
		return drugInPharmacyId;
	}

	public void setDrugInPharmacyId(DrugInPharmacyId drugInPharmacyId) {
		this.drugInPharmacyId = drugInPharmacyId;
	}

	public DateRange getPriceValidDateRange() {
		return priceValidDateRange;
	}

	public void setPriceValidDateRange(DateRange priceValidDateRange) {
		this.priceValidDateRange = priceValidDateRange;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
