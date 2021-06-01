package ISA.Team54.users.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ISA.Team54.users.enums.ComplaintType;

@Entity
public class Complaint {
	@Id
	@SequenceGenerator(name = "complaintSeqGen", sequenceName = "complaintSeq",initialValue = 4,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaintSeqGen")
	private Long id;
	
	@Column(unique = false,nullable = false)
	private String text;
	
	@Column(unique = false,nullable = false)
	private int objectId;
	
	@Enumerated(EnumType.STRING)
	private ComplaintType type;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Patient patient;
	
	@Column(unique = false,nullable = true)
	private Boolean responded;
	
	public Complaint() {
		super();
	}

	public Complaint(int objectId, String text, ComplaintType type) {
		super();
		this.objectId = objectId;
		this.text = text;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public ComplaintType getType() {
		return type;
	}

	public void setType(ComplaintType type) {
		this.type = type;
	}

	public Boolean getResponded() {
		return responded;
	}

	public void setResponded(Boolean responded) {
		this.responded = responded;
	}

	
	
	
	
}
