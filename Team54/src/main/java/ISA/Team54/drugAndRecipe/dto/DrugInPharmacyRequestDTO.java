package ISA.Team54.drugAndRecipe.dto;

public class DrugInPharmacyRequestDTO {
	private long pharmacyId;
	private long drugId;
	private int quantity;
	
	
	public DrugInPharmacyRequestDTO() {
		super();
	}

	public DrugInPharmacyRequestDTO(long pharmacyId, long drugId, int quantity) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugId = drugId;
		this.quantity = quantity;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public long getDrugId() {
		return drugId;
	}

	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
