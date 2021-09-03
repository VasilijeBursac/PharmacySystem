package ISA.Team54.users.dto;

public class PharmacistInfoDTO {
	long id;
	String name;
	String surname;
	String email;
	String phoneNumber;
	String address;
	String city;
	String country;
	double rating;
	long pharmacyId;
	double price;
	String pharmacyName;
	
	
	public PharmacistInfoDTO() {
		super();
	}
	
	public PharmacistInfoDTO(long id, String name, String surname, String email, String phoneNumber, String address, String city,
			String country, double rating, long pharmacyId, double price, String pharmacyName) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.country = country;
		this.rating = rating;
		this.pharmacyId = pharmacyId;
		this.price = price;
		this.pharmacyName = pharmacyName;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	} 
	
	
}
