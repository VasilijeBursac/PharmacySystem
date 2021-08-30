package ISA.Team54.users.dto;

import java.util.List;

import ISA.Team54.users.model.Pharmacy;

public class DermatologistInfoDTO {
	String name;
	String surname;
	String email;
	String phoneNumber;
	String address;
	String city;
	String country;
	double price;
	double rating;
	List<PharmacyDTO> pharmacies;
	
	
	public DermatologistInfoDTO() {
		super();
	}

	public DermatologistInfoDTO(String name, String surname, String email, String phoneNumber, String address,
			String city, String country, double price, double rating, List<PharmacyDTO> pharmacies) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.country = country;
		this.price = price;
		this.rating = rating;
		this.pharmacies = pharmacies;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<PharmacyDTO> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<PharmacyDTO> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}