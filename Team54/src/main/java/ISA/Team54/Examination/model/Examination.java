package ISA.Team54.Examination.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.Examination.enums.ExaminationStatus;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacy;

@Entity
public class Examination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = false, nullable = true)
	private String diagnose;

	@Column(unique = false, nullable = false)
	private int price;

	@Column(unique = false, nullable = true)
	private Integer therapyDuration;

	@Enumerated(EnumType.STRING)
	private ExaminationType type;

	@Enumerated(EnumType.STRING)
	private ExaminationStatus status;

	@Column(unique = false, nullable = false)
	private long emplyeedId;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Patient patient;

	@Embedded
	private Term term;

	@JsonBackReference	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Pharmacy pharmacy;

	// da li ce trebati nova tabela ovde za kolicinu? --proveritiii
	@JsonManagedReference
	@ManyToMany
	@JoinTable(name = "drugsInExamination", joinColumns = @JoinColumn(name = "examination_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"))
	public List<Drug> drugs;
	
	

	public Examination(long id, String diagnose, int price, Integer therapyDuration, ExaminationType type,
			ExaminationStatus status, long emplyeedId, Patient patient, Term term, Pharmacy pharmacy,
			List<Drug> drugs) {
		super();
		this.id = id;
		this.diagnose = diagnose;
		this.price = price;
		this.therapyDuration = therapyDuration;
		this.type = type;
		this.status = status;
		this.emplyeedId = emplyeedId;
		this.patient = patient;
		this.term = term;
		this.pharmacy = pharmacy;
		this.drugs = drugs;
	}

	public Examination(Examination examination) {
		super();
		this.id = examination.id;
		this.diagnose = examination.diagnose;
		this.price = examination.price;
		this.therapyDuration = examination.therapyDuration;
		this.type = examination.type;
		this.status = examination.status;
		this.emplyeedId = examination.emplyeedId;
		this.patient = examination.patient;
		this.term = examination.term;
		this.pharmacy = examination.pharmacy;
		this.drugs = examination.drugs;
	}
	
	public Examination() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {      
		this.id = id;  
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setTherapyDuration(Integer therapyDuration) {
		this.therapyDuration = therapyDuration;
	}

	public ExaminationType getType() {
		return type;
	}

	public void setType(ExaminationType type) {
		this.type = type;
	}

	public ExaminationStatus getStatus() {
		return status;
	}

	public void setStatus(ExaminationStatus status) {
		this.status = status;
	}

	public long getEmplyeedId() {
		return emplyeedId;
	}

	public void setEmplyeedId(long emplyeedId) {

		this.emplyeedId = emplyeedId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}

}
