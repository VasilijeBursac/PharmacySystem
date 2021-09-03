package ISA.Team54.drugOrdering.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DrugInOrderId implements Serializable {
	private long drugId;
	private long orderId;
	
	
	public DrugInOrderId() {
		super();
	}
	
	public DrugInOrderId(long drugId, long orderId) {
		super();
		this.drugId = drugId;
		this.orderId = orderId;
	}
	
	public DrugInOrderId(long drugId) {
		super();
		this.drugId = drugId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (drugId ^ (drugId >>> 32));
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrugInOrderId other = (DrugInOrderId) obj;
		if (drugId != other.drugId)
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}
	
	
}
