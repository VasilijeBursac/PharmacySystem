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
	private String type;
	private String shape;
	private String manufacturer;
	private String additionalInfo;
	
	public DrugInPharmacyDTO() {
		super();
	}

	public DrugInPharmacyDTO(long pharmacyId, long drugId, String drugName, String drugCode, int loyaltyPoints,
			int quantity, float price, double rating, String type, String shape, String manufacturer, String additionalInfo) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugId = drugId;
		this.drugName = drugName;
		this.drugCode = drugCode;
		this.loyaltyPoints = loyaltyPoints;
		this.quantity = quantity;
		this.price = price;
		this.rating = rating;
		this.type = type;
		this.shape = shape;
		this.manufacturer = manufacturer;
		this.additionalInfo = additionalInfo;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	

	
}