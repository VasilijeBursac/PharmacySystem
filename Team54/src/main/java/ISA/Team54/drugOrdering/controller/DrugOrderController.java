package ISA.Team54.drugOrdering.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ISA.Team54.drugOrdering.model.DrugInOrder;
import ISA.Team54.drugOrdering.model.DrugInOrderId;
import ISA.Team54.drugOrdering.model.DrugsOrder;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.drugOrdering.dto.DrugOrderDTO;
import ISA.Team54.drugOrdering.dto.DrugOrderRequestDTO;
import ISA.Team54.drugOrdering.dto.DrugInOrderDTO;
import ISA.Team54.drugOrdering.mapper.DrugInOrderMapper;
import ISA.Team54.drugOrdering.mapper.DrugOrderMapper;
import ISA.Team54.drugOrdering.service.interfaces.DrugInOrderService;
import ISA.Team54.drugOrdering.service.interfaces.DrugOrderService;
import ISA.Team54.promotion.dto.PromotionDTO;
import ISA.Team54.promotion.mappers.PromotionMapper;
import ISA.Team54.promotion.model.Promotion;
import ISA.Team54.users.model.Pharmacy;


@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugOrderController {

	@Autowired
	private DrugOrderService drugOrderService;
	
	@Autowired
	private DrugInOrderService drugInOrderService;
	
	@Autowired
	private DrugService drugService;
	
	@GetMapping("/allWaitingOrders")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<List<DrugOrderDTO>> getAllWaitingOrders(){
		try {
			List<DrugOrderDTO> drugOrderDTOs = new ArrayList<DrugOrderDTO>();
			drugOrderService.findAllWaitingOrders().forEach(drugOrder -> 						
				drugOrderDTOs.add(DrugOrderMapper.DrugOrderToDrugOrderDTO(drugOrder,getDrugsInOrder(drugOrder))));
			return new ResponseEntity<>(drugOrderDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private List<DrugInOrderDTO> getDrugsInOrder(DrugsOrder drugOrder){
		List<DrugInOrderDTO> drugsInOrderDTOs = new ArrayList<DrugInOrderDTO>();
		List<DrugInOrder> drugsInOrder = drugInOrderService.findAllDrugInOrdersByOrderId(drugOrder.getId());
		drugsInOrder.forEach(drugInOrder -> 
			drugsInOrderDTOs.add(new DrugInOrderDTO(drugOrder.getId(),drugInOrder.getId().getDrugId(), drugService.findById(drugInOrder.getId().getDrugId()).getName(), drugInOrder.getQuantity())));
		return drugsInOrderDTOs;
	}
	
	@GetMapping("/byPharmacyId/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<List<DrugOrderDTO>> getAllOrdersForPharmacy(@PathVariable long pharmacyId) {
		try {
			List<DrugOrderDTO> drugOrderDTOs = new ArrayList<DrugOrderDTO>();
			drugOrderService.getAllOrdersForPharmacy(pharmacyId).forEach(drugOrder -> 						
				drugOrderDTOs.add(DrugOrderMapper.DrugOrderToDrugOrderDTO(drugOrder,getDrugsInOrder(drugOrder))));
			return new ResponseEntity<>(drugOrderDTOs,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 		
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public  ResponseEntity<String> addOrder(@RequestBody DrugOrderRequestDTO drugOrderRequestDTO){
		try {
			List<DrugInOrder> drugsInOrder = new ArrayList<DrugInOrder>();
			
			drugOrderRequestDTO.getDrugsInOrder().forEach(drugInOrderRequestDTO -> 
			drugsInOrder.add(new DrugInOrder(new DrugInOrderId(drugInOrderRequestDTO.getDrugId()),
					   drugInOrderRequestDTO.getQuantity())));
			drugOrderService.addNewDrugOrder(drugOrderRequestDTO.getDeadline(), drugsInOrder);
			return new ResponseEntity<>( HttpStatus.CREATED);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	} 
}
