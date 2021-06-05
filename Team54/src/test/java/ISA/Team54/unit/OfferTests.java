package ISA.Team54.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ISA.Team54.drugOrdering.repository.OfferRepository;
import ISA.Team54.drugOrdering.service.interfaces.OfferService;
import ISA.Team54.drugOrdering.enums.OfferStatus;
import ISA.Team54.drugOrdering.model.Offer;
import ISA.Team54.loyalty.model.LoyaltyCategory;
import ISA.Team54.loyalty.model.LoyaltyCategoryIntertval;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Supplier;

import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.SupplierRepository;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OfferTests{
	
	@MockBean
	private SupplierRepository supplierRepository;
	
	@MockBean
	private OfferRepository offerRepository;
	
	@Autowired
	private OfferService offerService;
	
	//STUDENT 4 : UNIT TEST
	 @Test 
	 public void testFindAllOffersForSupplier() {
		Supplier supplier = new Supplier();
		supplier.setId(1L);
		supplier.setName("Filip");
		supplier.setSurname("Filipovic");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
		
        when(authentication.getPrincipal()).thenReturn(supplier);
        when(supplierRepository.findById(1L)).thenReturn(supplier);
        
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        Offer offer3 = new Offer();
        
        offer1.setStatus(OfferStatus.Waiting);
        offer2.setStatus(OfferStatus.Waiting);
        offer3.setStatus(OfferStatus.Waiting);
        offers.add(offer1);
        offers.add(offer2);
        offers.add(offer3);
        
        when(offerRepository.findAllBySupplier(supplier)).thenReturn(offers);
        
        assertEquals(3, offerService.findAllOffersForSupplier().size());
	 }
	
	 
	
}
