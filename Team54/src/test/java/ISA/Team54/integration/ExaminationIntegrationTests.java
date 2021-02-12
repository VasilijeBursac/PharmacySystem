package ISA.Team54.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	@Before
	public void login() {
		ResponseEntity<UserTokenState> responseEntity = restTemplate.postForEntity("/login",
				new JwtAuthenticationRequestDTO("marko@gmail.com", "marko"), UserTokenState.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * long id, String diagnose, int price, Integer therapyDuration, ExaminationType
	 * type, ExaminationStatus status, long emplyeedId, Patient patient, Term term,
	 * Pharmacy pharmacy, List<Drug> drugs
	 */
	@Test
	public void getAllExaminaitonsForPharmacy_ReturnsExaminations() {
		
		Pharmacy pharmacy = new Pharmacy(1L);
		
		Examination examination1 = new Examination(3L,"",1800,30,ExaminationType.DermatologistExamination,ExaminationStatus.Unfilled,1L,null,
				new Term(new Date(2020,12,15,12,0,0),30),pharmacy,null);
		Examination examination2 = new Examination(6L,"",1800,30,ExaminationType.DermatologistExamination,ExaminationStatus.Unfilled,1L,null,
				new Term(new Date(2020,12,15,12,0,0),30),pharmacy,null);
		Examination examination3 = new Examination(8L,"",1800,30,ExaminationType.DermatologistExamination,ExaminationStatus.Unfilled,1L,null,
				new Term(new Date(2020,12,15,12,0,0),30),pharmacy,null);
		Examination examination4 = new Examination(14L,"",1800,30,ExaminationType.DermatologistExamination,ExaminationStatus.Unfilled,1L,null,
				new Term(new Date(2020,12,15,12,0,0),30),pharmacy,null);
		Examination examination5 = new Examination(17L,"",1800,30,ExaminationType.DermatologistExamination,ExaminationStatus.Unfilled,1L,null,
				new Term(new Date(2020,12,15,12,0,0),30),pharmacy,null);
		List<Examination> examinations = new ArrayList<Examination>();
		
		examinations.add(examination1);
		examinations.add(examination2);
		examinations.add(examination3);
		examinations.add(examination4);
		examinations.add(examination5);
		
		List<DermatologistExaminationDTO> dermatologistExaminations = examinationService.getAllExaminationsForPharmacy(2L,ExaminationType.DermatologistExamination);
		
		assertEquals(dermatologistExaminations.size(), 5);
		
		for( int i = 0; i < dermatologistExaminations.size(); i++) {
			 assertEquals(dermatologistExaminations.get(i).getExaminationId(), examinations.get(i));
		 }
		
	}
	
	
}
