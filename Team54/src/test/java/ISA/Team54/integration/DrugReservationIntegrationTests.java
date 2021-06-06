package ISA.Team54.integration;

import ISA.Team54.Examination.model.Examination;
import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
import ISA.Team54.exceptions.DrugOutOfStockException;
import ISA.Team54.users.model.Patient;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrugReservationIntegrationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DrugReservationService drugReservationService;

    @Autowired
    private DrugReservationRepository drugReservationRepository;
   
    @Autowired
    private  DrugsInPharmacyRepository  drugsInPharmacyRepository;
    @Test
    public void getDrugReservationForPatient(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Patient patient = new Patient();
        patient.setId(5);
        patient.setName("Filip");
        patient.setSurname("Filipovic");
        when(authentication.getPrincipal()).thenReturn(patient);

        List<DrugReservation> reservations = drugReservationService.getReservationsForPatient();
        assertEquals(2, reservations.size());
    }

  //STUDENT 4 : INTEGRATION TEST
    @Test
	public void testReserveDrug() throws Exception{
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Patient patient = new Patient();
		patient.setId(5L);
		when(authentication.getPrincipal()).thenReturn(patient);
		int sizeBeforeAdding = drugReservationRepository.findAll().size();

		drugReservationService.reserveDrug(new DrugInPharmacyId(1L,5L),new Date());

		int sizeAfterAdding = drugReservationRepository.findAll().size();

		assertEquals(sizeBeforeAdding + 1,sizeAfterAdding);
	}
    
    
    //STUDENT 4 : INTEGRATION TEST
    @Test(expected =  DrugOutOfStockException.class)
	public void testReserveDrug_ThrowException() throws Exception{
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Patient patient = new Patient();
		patient.setId(5);
		patient.setName("Filip");
		patient.setSurname("Filipovic");
		when(authentication.getPrincipal()).thenReturn(patient);
		

		drugReservationService.reserveDrug(drugsInPharmacyRepository.findOneByDrugInPharmacyId(new DrugInPharmacyId(2L,1L)).getDrugInPharmacyId(),new Date());
	
		
	}
    
}
