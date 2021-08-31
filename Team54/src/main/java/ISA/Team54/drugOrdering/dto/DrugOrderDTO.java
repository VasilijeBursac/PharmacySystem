package ISA.Team54.drugOrdering.dto;

import java.util.Date;
import java.util.List;

import ISA.Team54.drugOrdering.enums.OrderStatus;

public class DrugOrderDTO {
	
	private long orderId;
	private Date deadline;
	private OrderStatus status;
	private String pharmacyName;
	private List<DrugInOrderDTO> drugsInOrder;
	private String pharmacyAdminFullName;
	
	public DrugOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugOrderDTO(long orderId, Date deadline, OrderStatus status, String pharmacyName, List<DrugInOrderDTO> drugsInOrder, String pharmacyAdminFullName) {
		super();
		this.orderId = orderId;
		this.deadline = deadline;
		this.status = status;
		this.pharmacyName = pharmacyName;
		this.drugsInOrder = drugsInOrder;
		this.pharmacyAdminFullName = pharmacyAdminFullName;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public List<DrugInOrderDTO> getDrugsInOrder() {
		return drugsInOrder;
	}

	public void setDrugsInOrder(List<DrugInOrderDTO> drugsInOrder) {
		this.drugsInOrder = drugsInOrder;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPharmacyAdminFullName() {
		return pharmacyAdminFullName;
	}

	public void setPharmacyAdminFullName(String pharmacyAdminFullName) {
		this.pharmacyAdminFullName = pharmacyAdminFullName;
	}
	
	
	
}
