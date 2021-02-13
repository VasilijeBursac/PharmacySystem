package ISA.Team54.integration;

import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        assertEquals(1, reservations.size());
    }

    @Test
    public void testCancelDrugReservation(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Patient patient = new Patient();
        patient.setId(5);
        patient.setName("Filip");
        patient.setSurname("Filipovic");
        when(authentication.getPrincipal()).thenReturn(patient);

        try {
            drugReservationService.cancelDrugReservation(2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrugReservation drugReservation = drugReservationRepository.findById(2L).orElse(null);
        assert drugReservation != null;
        assertEquals(ReservationStatus.Canceled,drugReservation.getStatus());
    }

}
