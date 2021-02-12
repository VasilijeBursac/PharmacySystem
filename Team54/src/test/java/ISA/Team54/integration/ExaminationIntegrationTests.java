package ISA.Team54.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import ISA.Team54.Examination.dto.DermatologistExaminationDTO;
import ISA.Team54.Examination.enums.ExaminationStatus;
import ISA.Team54.Examination.enums.ExaminationType;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.model.Term;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.shared.model.DateRange;
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
		
		List<DermatologistExaminationDTO> dermatologistExaminations = examinationService.getAllExaminationsForPharmacy(1L,ExaminationType.DermatologistExamination);
		
		assertEquals(dermatologistExaminations.size(), 5);
	}	
	
	@Test
	public void isDermatologistOnWorkInPharmacy_ReturnTrue() {
		
		boolean isOnWork = examinationService.isDermatologistOnWorkInTheParmacy(1L,1L,new DateRange(new Date(2021,02,22,11,30),new Date(2021,02,22,12,0)));
		assertTrue(isOnWork);
	}
	
	@Test
	public void isDermatologistOnWorkInPharmacy_ReturnFalse() {
		
		boolean isOnWork = examinationService.isDermatologistOnWorkInTheParmacy(1L,2L,new DateRange(new Date(2021,02,22,11,30),new Date(2021,02,22,12,30)));
		assertFalse(isOnWork);
	}
	@Test
	public void isDermatologistAvailable_ReturnsTrue() {
		boolean isAvailable = examinationService.isDermatologistAvailable(1L,1L,new Date(2021,02,22,11,30),new Date(2021,02,22,12,0));
		assertTrue(isAvailable);
	}
	
	@Test
	public void isDermatologistAvailable_ReturnsFalse() {
		boolean isAvailable = examinationService.isDermatologistAvailable(1L,1L,new Date(2021,02,22,17,30),new Date(2021,02,22,18,0));
		assertFalse(isAvailable);
	}
	
	@Test
	public void isPatientAvailable_ReturnTrue() {
		boolean isAvailable = examinationService.isPatientAvailable(5L,new Date(2021,02,22,17,30),new Date(2021,02,22,18,0));
		assertTrue(isAvailable);
	}
}
