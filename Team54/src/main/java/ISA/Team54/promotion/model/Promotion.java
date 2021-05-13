package ISA.Team54.promotion.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ISA.Team54.shared.model.DateRange;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacy;

@Entity
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String description;
	
	@Embedded
	private DateRange dateInterval;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)	
	private Pharmacy pharmacy;

	public Promotion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Promotion(long id, String description, DateRange dateInterval, Pharmacy pharmacy) {
		super();
		this.id = id;
		this.description = description;
		this.dateInterval = dateInterval;
		this.pharmacy = pharmacy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateRange getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(DateRange dateInterval) {
		this.dateInterval = dateInterval;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	 
	
}
