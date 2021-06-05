package ISA.Team54.integration;

import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.service.interfaces.PatientService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PatientIntegrationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;


    //STUDENT 4 : INTEGRATION TEST
    @Test(expected =  Exception.class)
    public void testCheckForSubscription() {
    	 Authentication authentication = Mockito.mock(Authentication.class);
		 SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		 Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		 SecurityContextHolder.setContext(securityContext);
		
		 Patient patient = new Patient();
		 patient.setId(5L);

		 when(authentication.getPrincipal()).thenReturn(patient);
		 boolean flag = patientService.checkForSubscription(1L);

    }
    
  //STUDENT 4 : INTEGRATION TEST
    @Test
    public void testAddLoyaltyPointsForReservedDrug() {
    	 Authentication authentication = Mockito.mock(Authentication.class);
		 SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		 Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		 SecurityContextHolder.setContext(securityContext);
		
		 Patient patient = new Patient();
		 patient.setId(5L);
		 when(authentication.getPrincipal()).thenReturn(patient);
		 patientService.addLoyaltyPointsForReservedDrug(1L);
		
		 assertEquals(125,  patientRepository.findById(5L).getLoyaltyPoints());

    }
}
