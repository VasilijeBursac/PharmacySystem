package ISA.Team54.drugOrdering.mapper;

import ISA.Team54.drugOrdering.dto.DrugInOrderDTO;
import ISA.Team54.drugOrdering.dto.DrugInOrderRequestDTO;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugInOrderId;

public class DrugInOrderMapper {
	public static DrugInOrder DrugInOrderDTOToDrugInOrder(DrugInOrderDTO drugInOrderDTO) {
		return new DrugInOrder(new DrugInOrderId(drugInOrderDTO.getDrugId(), drugInOrderDTO.getOrderId()),
							   drugInOrderDTO.getQuantity());
	}
	
	public static DrugInOrder DrugInOrderRequestDTOToDrugInOrder(DrugInOrderRequestDTO drugInOrderRequestDTO) {
		return new DrugInOrder(new DrugInOrderId(drugInOrderRequestDTO.getDrugId()),
							   drugInOrderRequestDTO.getQuantity());
	}
}
