package ISA.Team54.drugAndRecipe.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class DrugInERecipe {
	@EmbeddedId
	private DrugInERecipeId id;
	
	@Column(unique = false, nullable = false)
	private int quantityInERecipe;

	public DrugInERecipe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInERecipe(DrugInERecipeId id, int quantityInERecipe) {
		super();
		this.id = id;
		this.quantityInERecipe = quantityInERecipe;
	}

	public DrugInERecipeId getId() {
		return id;
	}

	public void setId(DrugInERecipeId id) {
		this.id = id;
	}

	public int getQuantityInERecipe() {
		return quantityInERecipe;
	}

	public void setQuantityInERecipe(int quantityInERecipe) {
		this.quantityInERecipe = quantityInERecipe;
	}
	
	
}
