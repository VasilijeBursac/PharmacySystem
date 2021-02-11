package ISA.Team54.drugAndRecipe.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import ISA.Team54.Examination.model.Examination;
import ISA.Team54.rating.model.Rating;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Drug {
	@Id
	@SequenceGenerator(name = "DrugSG", sequenceName = "DrugSeq",initialValue = 6,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DrugSG")
	private long id;
	@Column(unique = false,nullable = false)
	private String name;
	@Column(unique = false,nullable = false)
	private String code;
	@Column(unique = false,nullable = false)
	private String type;
	@Column(unique = false,nullable = false)
	private String shape;
	@Column(unique = false,nullable = false)
	private String manifacturer;
	@Column(unique = false,nullable = true)
	private String additionalInfo;
	@Column(unique = false,nullable = true)
	private int loyaltyPoints;
	
	@ManyToMany(mappedBy="drugs")
	private Set<Examination> examinations;
	
	@ManyToMany(mappedBy="drugs")
	private Set<ERecipe> erecipes;

	@OneToOne(mappedBy = "drug",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private DrugSpecification drugSpecification;

	@JsonManagedReference	
	@OneToMany(mappedBy = "drug", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Rating> ratings;
	
	@JsonManagedReference
	@ManyToMany
	@JoinTable(name="substituteDrugs",
	 joinColumns=@JoinColumn(name="mainDrugId"),
	 inverseJoinColumns=@JoinColumn(name="substituteDrugId")
	)
	private List<Drug> substituteDrugs;

	@JsonBackReference
	@ManyToMany
	@JoinTable(name="substituteDrugs",
	 joinColumns=@JoinColumn(name="substituteDrugId"),
	 inverseJoinColumns=@JoinColumn(name="mainDrugId")
	)
	private List<Drug> mainDrugs;
	
	public Drug() {
		super();
	}

	public Drug(String name, String code, String type, String shape, String manifacturer, String additionalInfo,int loyaltyPoints, List<Drug> substituteDrugs) {
		super();
		this.name = name;
		this.code = code;
		this.type = type;
		this.shape = shape;
		this.manifacturer = manifacturer;
		this.additionalInfo = additionalInfo;		
		this.loyaltyPoints = loyaltyPoints;
		this.substituteDrugs = substituteDrugs;
	}
	
	public long getId() {
		return  id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLoyalityPoints() {
		return loyaltyPoints;
	}

	public void setLoyalityPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	public Set<ERecipe> getErecipes() {
		return erecipes;
	}

	public void setErecipes(Set<ERecipe> erecipes) {
		this.erecipes = erecipes;
	}

	public DrugSpecification getDrugSpecification() {
		return drugSpecification;
	}

	public void setDrugSpecification(DrugSpecification drugSpecification) {
		this.drugSpecification = drugSpecification;
	}

	public List<Drug> getMainDrugs() {
		return mainDrugs;
	}

	public void setMainDrugs(List<Drug> mainDrugs) {
		this.mainDrugs = mainDrugs;
	}

	public List<Drug> getSubstituteDrugs() {
		return substituteDrugs;
	}

	public void setSubstituteDrugs(List<Drug> substituteDrugs) {
		this.substituteDrugs = substituteDrugs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getManifacturer() {
		return manifacturer;
	}

	public void setManifacturer(String manifacturer) {
		this.manifacturer = manifacturer;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	
	
}
