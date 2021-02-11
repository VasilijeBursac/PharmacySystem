package ISA.Team54.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ISA.Team54.Examination.enums.ExaminationStatus;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.Examination.service.interfaces.ExaminationService;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.User;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.service.interfaces.DermatologistService;
import ISA.Team54.users.service.interfaces.PatientService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserTests{
	@MockBean
	private DermatologistRepository dermatologistRepositoryMocked;
	
	@MockBean
	private PatientRepository patientRepositoryMocked;
	
	@MockBean
	private ExaminationService examinationServiceMocked;
	
	@MockBean
	private ExaminationRepository examinationRepositoryMocked;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	 @Test 
	 public void findDermatologistById_ReturnsDermatologist() {
		 Dermatologist dermatologistTest = new 	 Dermatologist();
		 dermatologistTest.setId(1L);
		 Mockito.when(dermatologistRepositoryMocked.findOneById(dermatologistTest.getId())).thenReturn(dermatologistTest);
		 Dermatologist dermatologist =	dermatologistService.findOneById(Constants.DERMATOLOGIST_ID1);
		 assertEquals(Constants.DERMATOLOGIST_ID1, dermatologist.getId());
	 }
	
	 
	 @Test 
	 public void findPatientBySurnameAndName_ReturnPatient(String surnameAndName) {
		 Patient patientTest1 = new Patient();
		 patientTest1.setName("Milica");
		 patientTest1.setSurname("Ivanovic");
		 Set<User> results = new HashSet<User>();
		 List<User> returnedPatients = new ArrayList<User>();
		 returnedPatients.add(patientTest1);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest1.getName(),patientTest1.getSurname())).thenReturn(returnedPatients);
		 Mockito.when(patientRepositoryMocked.findAll()).thenReturn(new ArrayList<Patient>());
		 results = patientService.findBySurnameAndName(Constants.PATIENT_NAME_1+" "+Constants.PATIENT_SURNAME_1);
		 assertNotNull(results);
	 }	 
	 
	 @Test 
	 public void findPatientBySurnameAndName_ReturnsAllPatients(String surnameAndName) {
		 
		 Patient patientTest1 = new Patient();
		 patientTest1.setName("Milica");
		 patientTest1.setSurname("Ivanovic");
		 Patient patientTest2 = new Patient();
		 patientTest1.setName("Jakov");
		 patientTest1.setSurname("Jon");
		 Patient patientTest3 = new Patient();
		 patientTest1.setName("David");
		 patientTest1.setSurname("Vojvodic");
		 Patient patientTest4 = new Patient();
		 patientTest1.setName("Milica");
		 patientTest1.setSurname("Marinkovic");
		 Patient patientTest5 = new Patient();
		 patientTest1.setName("Marina");
		 patientTest1.setSurname("Joksimovic");
		 
		 Set<User> results = new HashSet<User>();
		 List<User> returnedPatients1 = new ArrayList<User>();
		 returnedPatients1.add(patientTest1);
		 List<User> returnedPatients2 = new ArrayList<User>();
		 returnedPatients1.add(patientTest2);
		 List<User> returnedPatients3 = new ArrayList<User>();
		 returnedPatients1.add(patientTest3);
		 List<User> returnedPatients4 = new ArrayList<User>();
		 returnedPatients1.add(patientTest4);
		 List<User> returnedPatients5 = new ArrayList<User>();
		 returnedPatients1.add(patientTest5);
		 List<Patient> allPatients = new ArrayList<Patient>();
		 allPatients.add(patientTest1);
		 allPatients.add(patientTest2);
		 allPatients.add(patientTest3);
		 allPatients.add(patientTest4);
		 allPatients.add(patientTest5);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest1.getName(),patientTest1.getSurname())).thenReturn(returnedPatients1);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest2.getName(),patientTest2.getSurname())).thenReturn(returnedPatients2);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest3.getName(),patientTest3.getSurname())).thenReturn(returnedPatients3);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest4.getName(),patientTest4.getSurname())).thenReturn(returnedPatients4);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest5.getName(),patientTest5.getSurname())).thenReturn(returnedPatients5);
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest1.getSurname(),patientTest1.getName())).thenReturn(new ArrayList<User>());
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest2.getSurname(),patientTest2.getName())).thenReturn(new ArrayList<User>());
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest3.getSurname(),patientTest3.getName())).thenReturn(new ArrayList<User>());
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest4.getSurname(),patientTest4.getName())).thenReturn(new ArrayList<User>());
		 Mockito.when(patientRepositoryMocked.findByNameAndSurnameIgnoreCaseIn(patientTest5.getSurname(),patientTest5.getName())).thenReturn(new ArrayList<User>());
		 Mockito.when(patientRepositoryMocked.findAll()).thenReturn(allPatients);
		 results = patientService.findBySurnameAndName("*");
		 assertEquals(results.size(), allPatients.size());
	 }
}
