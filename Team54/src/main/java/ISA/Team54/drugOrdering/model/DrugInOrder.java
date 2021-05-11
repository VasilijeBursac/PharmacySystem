package ISA.Team54.drugOrdering.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ISA.Team54.drugAndRecipe.model.Drug;

@Entity
public class DrugInOrder {
	
	@EmbeddedId
	private DrugInOrderId id;
	
	@Column(unique = false,nullable = true)
	private int quantity;

	public DrugInOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInOrder(DrugInOrderId id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public DrugInOrderId getId() {
		return id;
	}

	public void setId(DrugInOrderId id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
