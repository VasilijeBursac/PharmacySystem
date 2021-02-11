package ISA.Team54.rating.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.Pharmacy;

@Entity
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
	@ManyToOne
	@Nullable
	private Dermatologist dermatologist;
	
	@JsonBackReference
	@ManyToOne
	@Nullable
	private Pharmacist pharmacist;
	
	@JsonBackReference
	@ManyToOne
	@Nullable
	private Pharmacy pharmacy;

	@OneToOne
	private Patient patient;
	
	@JsonBackReference
	@ManyToOne
	@Null
	private Drug drug;
	
	@Column(unique = false,nullable = false)
	private double rating;
	
	public Rating() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dermatologist getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(Dermatologist dermatoligist) {
		this.dermatologist = dermatoligist;
	}

	public Pharmacist getPharmacist() {
		return pharmacist;
	}

	
	public void setPharmacist(Pharmacist pharmacist) {
		this.pharmacist = pharmacist;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}
		
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
