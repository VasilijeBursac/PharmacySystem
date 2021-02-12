package ISA.Team54.integration;

import ISA.Team54.loyalty.model.LoyaltyCategory;
import ISA.Team54.loyalty.service.LoyaltyService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class LoyaltyIntegrationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LoyaltyService loyaltyService;


    @Test
    public void testGetPatientLoyaltyCategory() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Patient patient = new Patient();
        patient.setId(5);
        patient.setName("Filip");
        patient.setSurname("Filipovic");
        patient.setPenaltyPoints(80);

        when(authentication.getPrincipal()).thenReturn(patient);
        LoyaltyCategory category = loyaltyService.getLoyaltyCategory();
        assertEquals("Gold", category.getName());
    }
}
