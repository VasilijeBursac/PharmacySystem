package ISA.Team54.drugAndRecipe.dto;

import java.util.List;

import ISA.Team54.drugAndRecipe.model.Contraindication;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.Ingredient;

public class DrugSpecificationDTO {
	private List<ContraindicationDTO> contraindications;
	private List<IngredientDTO> ingredients;
	private String suggestedDose;

	public DrugSpecificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DrugSpecificationDTO(List<ContraindicationDTO> contraindications, List<IngredientDTO> ingredients,
			String suggestedDose, List<DrugDTO> substitute) {
		super();
		this.contraindications = contraindications;
		this.ingredients = ingredients;
		this.suggestedDose = suggestedDose;
	}

	public DrugSpecificationDTO(List<ContraindicationDTO> contraindications,List<IngredientDTO> ingredients,
			String suggestedDose) {
		super();
		this.contraindications = contraindications;
		this.ingredients = ingredients;
		this.suggestedDose = suggestedDose;
	}

	public List<ContraindicationDTO> getContraindications() {
		return contraindications;
	}
	public void setContraindications(List<ContraindicationDTO> contraindication) {
		this.contraindications = contraindication;
	}
	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}
	public String getSuggestedDose() {
		return suggestedDose;
	}
	public void setSuggestedDose(String suggestedDose) {
		this.suggestedDose = suggestedDose;
	}
	
	
}
