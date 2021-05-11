package ISA.Team54.drugAndRecipe.dto;

public class DrugInStorageDTO {
	
	private String drugName;
	private int quantity;
	
	public DrugInStorageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInStorageDTO(String drugName, int quantity) {
		super();
		this.drugName = drugName;
		this.quantity = quantity;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
