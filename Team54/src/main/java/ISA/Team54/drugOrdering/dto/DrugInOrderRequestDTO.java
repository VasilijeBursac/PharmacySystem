package ISA.Team54.drugOrdering.dto;

public class DrugInOrderRequestDTO {
	private long drugId;
	private int quantity;
	
	public DrugInOrderRequestDTO() {
		super();
	}
	
	public DrugInOrderRequestDTO(long drugId, int quantity) {
		super();
		this.drugId = drugId;
		this.quantity = quantity;
	}

	public long getDrugId() {
		return drugId;
	}

	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
