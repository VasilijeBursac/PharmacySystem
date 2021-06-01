package ISA.Team54.drugAndRecipe.dto;

public class DrugForERecipeDTO {

	private String code;
	private String name;
	private int quantity;
	
	public DrugForERecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugForERecipeDTO(String code, String name, int quantity) {
		super();
		this.code = code;
		this.name = name;
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
