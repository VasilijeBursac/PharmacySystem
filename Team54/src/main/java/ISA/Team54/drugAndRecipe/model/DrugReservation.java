package ISA.Team54.drugAndRecipe.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.users.model.Patient;
import org.springframework.data.annotation.Version;

@Entity
public class DrugReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = false,nullable = true)
	private Date reservationToDate;
	
	@Column(unique = false,nullable = true)
	private ReservationStatus status; 
	
	@Column(unique = false, nullable = true)
	private float sellingPrice;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Patient patient;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private DrugInPharmacy reservedDrug;

	@Version
	private Long version;
	
	public DrugReservation() {
		super();
	}

	public DrugReservation(long id, Date reservationToDate, ReservationStatus status, Patient patient) {
		this.id = id;
		this.reservationToDate = reservationToDate;
		this.status = status;
		this.patient = patient;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getReservationToDate() {
		return reservationToDate;
	}

	public void setReservationToDate(Date reservationToDate) {
		this.reservationToDate = reservationToDate;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public DrugInPharmacy getReservedDrug() {
		return reservedDrug;
	}

	public void setReservedDrug(DrugInPharmacy reservedDrug) {
		this.reservedDrug = reservedDrug;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public float getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	

}
