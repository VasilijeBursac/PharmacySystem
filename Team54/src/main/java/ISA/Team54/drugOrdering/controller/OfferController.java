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

import ISA.Team54.drugOrdering.dto.DrugOrderDTO;
import ISA.Team54.drugOrdering.dto.OfferDTO;
import ISA.Team54.drugOrdering.exceptions.DeadlineNotOverException;
import ISA.Team54.drugOrdering.exceptions.NotEnoughDrugsInStorageException;
import ISA.Team54.drugOrdering.exceptions.NotOrderAuthorException;
import ISA.Team54.drugOrdering.exceptions.OrderAlreadyFulfilledException;
import ISA.Team54.drugOrdering.mapper.DrugOrderMapper;
import ISA.Team54.drugOrdering.mapper.OfferMapper;
import ISA.Team54.drugOrdering.model.Offer;
import ISA.Team54.drugOrdering.service.interfaces.DrugOrderService;
import ISA.Team54.drugOrdering.service.interfaces.OfferService;

@RestController
@RequestMapping(value = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {

	@Autowired
	OfferService offerService;
	
	@Autowired
	DrugOrderService drugOrderService;
	
	@PostMapping("/addOffer")
	@PreAuthorize("hasRole('SUPPLIER')")
	public  ResponseEntity<String> addOffer(@RequestBody OfferDTO offerDTO){
		try {
			Offer offer = OfferMapper.OfferDTOIntoOffer(offerDTO);
			offer.setOrder(drugOrderService.findById(offerDTO.getOrderId()));
			offerService.addOffer(offer);
			return new ResponseEntity<>(HttpStatus.CREATED);	
		} catch (NotEnoughDrugsInStorageException e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}		 
	}
	
	@GetMapping("/allOffersForSupplier")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<List<OfferDTO>> getAllOffersForSupplier(){
		try {
			List<OfferDTO> offerDTOs = new ArrayList<OfferDTO>();
			offerService.findAllOffersForSupplier().forEach(offer -> 
				offerDTOs.add(OfferMapper.OfferIntoOfferDTO(offer)));									
			return new ResponseEntity<>(offerDTOs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/byOrderId/{orderId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<List<OfferDTO>> getAllOffersForOrder(@PathVariable long orderId){
		try {
			List<OfferDTO> offerDTOs = new ArrayList<OfferDTO>();
			offerService.getAllOffersForOrder(orderId).forEach(offer -> 
				offerDTOs.add(OfferMapper.OfferIntoOfferDTO(offer)));									
			return new ResponseEntity<>(offerDTOs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/acceptOffer/{offerId}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	public ResponseEntity<String> acceptOffer(@PathVariable long offerId){
		try {
			offerService.acceptOffer(offerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (OrderAlreadyFulfilledException e) {
			return new ResponseEntity<>("Narudžbenica je već obrađena!", HttpStatus.BAD_REQUEST);
		} catch (NotOrderAuthorException e) {
			return new ResponseEntity<String>("Samo administrator koji je kreirao narudžbenicu može da odabere ponudu!", HttpStatus.BAD_REQUEST);
		} catch (DeadlineNotOverException e) {
			return new ResponseEntity<>("Rok za davanje ponuda još nije istekao!", HttpStatus.BAD_REQUEST);
		} catch (Exception e){
	        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	    } 
	}
}
