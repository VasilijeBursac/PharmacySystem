package ISA.Team54.drugAndRecipe.dto;

import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;

public class DrugInPharmacyDTO {
	private long pharmacyId;
	private long drugId;
	private String drugName;
	private String drugCode;
	private int loyaltyPoints;
	private int quantity;
	private float price;
	private double rating;
	
	public DrugInPharmacyDTO() {
		super();
	}

	public DrugInPharmacyDTO(long pharmacyId, long drugId, String drugName, String drugCode, int loyaltyPoints,
			int quantity, float price, double rating) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugId = drugId;
		this.drugName = drugName;
		this.drugCode = drugCode;
		this.loyaltyPoints = loyaltyPoints;
		this.quantity = quantity;
		this.price = price;
		this.rating = rating;
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

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	

	
}