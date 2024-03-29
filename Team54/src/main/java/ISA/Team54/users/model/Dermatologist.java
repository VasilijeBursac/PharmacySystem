package ISA.Team54.users.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ISA.Team54.rating.model.Rating;
import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;
import ISA.Team54.vacationAndWorkingTime.model.VacationRequest;

@Entity
public class Dermatologist extends User{
	@Column(unique = false,nullable = true)
	private double price;
	
	@JsonManagedReference	
	@OneToMany(mappedBy = "dermatologist", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Rating> ratings;
	
	@OneToMany(mappedBy="dermatologist",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<DermatologistWorkSchedule> workSchedules;
		
	@ManyToMany(mappedBy = "dermatologists")
	private List<Pharmacy> pharmacies;
	
	@OneToMany(mappedBy = "dermatologist",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<VacationRequest> vacationRequests;
	
	public Dermatologist() {
		super();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRatings() {
		double rating = 0;
		int count = 0;
		if(ratings != null) {
			for (Rating r : ratings) {
				rating += r.getRating();
				count++;
			}
		}
		
		return count != 0 ? (double)rating/count : 0;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<DermatologistWorkSchedule> getWorkSchedule() {
		return workSchedules;
	}

	public void setWorkSchedule(List<DermatologistWorkSchedule> workSchedule) {
		this.workSchedules = workSchedule;
	}

	public List<Pharmacy> getPharmacy() {
		return pharmacies;
	}

	public void setPharmacy(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	
}
