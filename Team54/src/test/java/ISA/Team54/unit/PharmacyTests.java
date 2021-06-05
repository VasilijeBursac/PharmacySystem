package ISA.Team54.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


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

import ISA.Team54.loyalty.model.LoyaltyCategory;
import ISA.Team54.loyalty.model.LoyaltyCategoryIntertval;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.service.interfaces.PharmacyService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PharmacyTests{

	@MockBean
	private PatientRepository patientRepositoryMocked;
	
	@MockBean
	private LoyaltyRepository loyaltyRepository;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	
	//STUDENT 4 : UNIT TEST
	 @Test 
	 public void testGetPharmacistPriceWithDiscount() {
		Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Filip");
        patient.setSurname("Filipovic");
        patient.setLoyaltyPoints(80);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
		
        when(authentication.getPrincipal()).thenReturn(patient);
        when(patientRepositoryMocked.findById(1L)).thenReturn(patient);
        LoyaltyCategory category = new LoyaltyCategory();
        category.setId(2L);
        category.setInterval(new LoyaltyCategoryIntertval(100,51));
        category.setName("Silver");
        category.setDiscount(20);
        when(loyaltyRepository.getLoyaltyCategory(patient.getLoyaltyPoints())).thenReturn(category);
        
        assertEquals(pharmacyService.getPharmacistPriceWithDiscount(1200), 0.01 * 1200 * 80);
	 }
	
	 
	
}
