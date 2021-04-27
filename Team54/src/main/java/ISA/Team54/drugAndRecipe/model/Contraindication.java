package ISA.Team54.drugAndRecipe.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="contraindications")
public class Contraindication {

	@Id
	@SequenceGenerator(name = "ContraindicationSG", sequenceName = "ContraindicationSeq",initialValue = 5,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContraindicationSG")
	private long id;
	
	@Column(unique = false,nullable = true)
	private String name;
	
	@JsonBackReference(value="clinci_movement")
	@ManyToMany(mappedBy = "contraindications",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<DrugSpecification> drugSpecifications = new ArrayList<DrugSpecification>();
	
	public Contraindication() {
		super();
	}
	
	public Contraindication(String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
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

	public List<DrugSpecification> getDrugSpecifications() {
		return drugSpecifications;
	}

	public void setDrugSpecifications(List<DrugSpecification> drugSpecifications) {
		this.drugSpecifications = drugSpecifications;
	}
	
}
