package ISA.Team54.users.dto;

import ISA.Team54.shared.model.DateRange;

public class DermatologistInPharmacyInfoDTO {
	long id;
	String name;
	String surname;
	String email;
	String phoneNumber;
	String address;
	String city;
	String country;
	double price;
	double rating;
	long pharmacyId;
	DateRange pharmacyWorkSchedule;
}
