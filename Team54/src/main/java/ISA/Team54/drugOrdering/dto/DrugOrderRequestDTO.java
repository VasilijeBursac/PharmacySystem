package ISA.Team54.drugOrdering.dto;

import java.util.Date;
import java.util.List;

public class DrugOrderRequestDTO {
	private Date deadline;
	private List<DrugInOrderRequestDTO> drugsInOrder;
	
	public DrugOrderRequestDTO() {
		super();
	}
	
	public DrugOrderRequestDTO(Date deadline, List<DrugInOrderRequestDTO> drugsInOrder) {
		super();
		this.deadline = deadline;
		this.drugsInOrder = drugsInOrder;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public List<DrugInOrderRequestDTO> getDrugsInOrder() {
		return drugsInOrder;
	}
	public void setDrugsInOrder(List<DrugInOrderRequestDTO> drugsInOrder) {
		this.drugsInOrder = drugsInOrder;
	}
	
	
	
}
