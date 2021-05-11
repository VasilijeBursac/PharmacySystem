package ISA.Team54.drugOrdering.mapper;

import java.util.List;

import ISA.Team54.drugOrdering.dto.DrugInOrderDTO;
import ISA.Team54.drugOrdering.dto.DrugOrderDTO;
import ISA.Team54.drugOrdering.model.DrugsOrder;

public class DrugOrderMapper {

	public static DrugOrderDTO DrugOrderToDrugOrderDTO(DrugsOrder drugOrder,List<DrugInOrderDTO> drugsInOrder) {
		return new DrugOrderDTO(drugOrder.getId(),
								drugOrder.getDeadline(),
								drugOrder.getStatus(),
								drugOrder.getAdministrator().getPharmacy().getName(),
								drugsInOrder);
	}
}
