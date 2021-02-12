package ISA.Team54.users.dto;

import javax.persistence.Column;

public class PharmacyAdministratorDTO {

	private int id;
	private String email;
	private String name;
	private String surname;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;	
	private Boolean confirmed;
	private int pharmacyId;
	
	
	
	public PharmacyAdministratorDTO() {
		super();
	}
	
	public PharmacyAdministratorDTO(int id, String email, String name, String surname, String address, String city,
			String country, String phoneNumber, Boolean confirmed, int pharmacyId) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.confirmed = confirmed;
		this.pharmacyId = pharmacyId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public int getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	
}
