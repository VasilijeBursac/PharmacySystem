package ISA.Team54.drugOrdering.dto;

public class DrugInOrderDTO {
	
	private long orderId;
	private long drugId;
	private String drugName;
	private int quantity;
	
	public DrugInOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugInOrderDTO(long orderId, long drugId, String drugName, int quantity) {
		super();
		this.orderId = orderId;
		this.drugId = drugId;
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

	public long getDrugId() {
		return drugId;
	}

	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	
}
