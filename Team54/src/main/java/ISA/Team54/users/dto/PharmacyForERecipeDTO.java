package ISA.Team54.users.dto;

public class PharmacyForERecipeDTO {
	
	private long pharmacyId;
	private String pharmacyName;
	private double pharmacyRating;
	private float totalPrice;
	private String city;
	
	public PharmacyForERecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PharmacyForERecipeDTO(long pharmacyId, String pharmacyName, double pharmacyRating, float totalPrice, String city) {
		super();
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
		this.pharmacyRating = pharmacyRating;
		this.totalPrice = totalPrice;
		this.city = city;
	}
	
	public long getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public double getPharmacyRating() {
		return pharmacyRating;
	}
	public void setPharmacyRating(double pharmacyRating) {
		this.pharmacyRating = pharmacyRating;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
