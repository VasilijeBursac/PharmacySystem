package ISA.Team54.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.sql.Time;
import java.util.*;

import javax.annotation.PostConstruct;

import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.users.model.Patient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ISA.Team54.Examination.dto.DermatologistExaminationDTO;
import ISA.Team54.Examination.enums.ExaminationStatus;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.model.Term;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.security.UserTokenState;
import ISA.Team54.security.auth.JwtAuthenticationRequestDTO;
import ISA.Team54.users.model.Pharmacy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ExaminationIntegrationTests {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private MockMvc mockMvc;
	private String accessToken;
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ExaminationService examinationService;

	@Autowired
	private ExaminationRepository examinationRepository;

	/*
	 * long id, String diagnose, int price, Integer therapyDuration, ExaminationType
	 * type, ExaminationStatus status, long emplyeedId, Patient patient, Term term,
	 * Pharmacy pharmacy, List<Drug> drugs
	 */

	@Test
	public void testGetFutureExaminations(){
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Patient patient = new Patient();
		patient.setId(5);
		patient.setName("Filip");
		patient.setSurname("Filipovic");
		when(authentication.getPrincipal()).thenReturn(patient);

		List<DermatologistExaminationDTO> examinations = examinationService.getFutureExaminations(ExaminationType.PharmacistExamination);
		assertEquals(examinations.size(), 2);
	}

	@Test
	public void testScheduleExamination(){
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Patient patient = new Patient();
		patient.setId(5);
		patient.setName("Filip");
		patient.setSurname("Filipovic");
		when(authentication.getPrincipal()).thenReturn(patient);

		examinationService.scheduleExamination(10L);
		Examination examination = examinationRepository.findById(10L).orElse(null);
		assert examination != null;
		assertEquals(5L,examination.getPatient().getId());
	}

	/*
	@Test
	public void testGetFreePharmaciesForInterval(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("CET"));
		cal.set(Calendar.YEAR, 2021);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		List<Pharmacy> pharmacies = examinationService.getFreePharmaciesForInterval(cal.getTime(),ExaminationType.PharmacistExamination);
		assertEquals(2L, pharmacies.get(0).getId());
	}*/
	
	
}
