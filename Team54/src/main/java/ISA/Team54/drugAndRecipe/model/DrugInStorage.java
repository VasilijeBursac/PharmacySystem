package ISA.Team54.drugAndRecipe.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ISA.Team54.users.model.Supplier;

@Entity
public class DrugInStorage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = false,nullable = false)
	private int quantity;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Drug drug;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Supplier supplier;

	public DrugInStorage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInStorage(long id, int quantity, Drug drug, Supplier supplier) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.drug = drug;
		this.supplier = supplier;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}	
	
	
}
