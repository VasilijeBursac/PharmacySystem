package ISA.Team54.drugOrdering.dto;

public class DrugInOrderDTO {
	
	private String drugName;
	private int quantity;
	
	public DrugInOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInOrderDTO(String drugName, int quantity) {
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
