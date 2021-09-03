package ISA.Team54.drugAndRecipe.dto;

public class PharmacyForDrugDTO {
	private long pharmacyId;
	private String pharmacyName;
	private String address;
	private double drugPrice;
	
	public PharmacyForDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PharmacyForDrugDTO(long pharmacyId, String pharmacyName, String address, double drugPrice) {
		super();
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
		this.address = address;
		this.drugPrice = drugPrice;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getDrugPrice() {
		return drugPrice;
	}

	public void setDrugPrice(double drugPrice) {
		this.drugPrice = drugPrice;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
	
}
