package ISA.Team54.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrugTests {

	@MockBean
	private PatientRepository patientRepositoryMocked;
	
	@MockBean
	private DrugRepository drugRepositoryMocked;
	
	@MockBean
	private DrugReservationRepository drugReservationRepositoryMocked;
	
	@Autowired
	private DrugReservationService drugReservationService;
	
	@Autowired
	private DrugService drugService;
	
	
	@Test void  isPatientAlergicOnDrugTest(){
		Drug drug = new Drug(1,"Aspirin","23425",3);
		List<Drug> drugAllergies = new ArrayList<Drug>();
		drugAllergies.add(new Drug(1,"Aspirin","23425",3));
		drugAllergies.add(new Drug(2,"Paracetamol","23421",4));
		drugAllergies.add(new Drug(3,"Kafetin","23475",2));
		Patient patient = new Patient();
		patient.setDrugAllergies(drugAllergies);
		Mockito.when(drugRepositoryMocked.findOneById(1L)).thenReturn(drug);
		Mockito.when(patientRepositoryMocked.findOneById(1L)).thenReturn(patient);
		boolean isAllergic = drugService.isPatientAlergicOnDrug(Constants.PATIENT_ID1,Constants.DRUG_ID1);
		assertTrue(isAllergic);
	}	
	
	 @Test public void getReservationsForPatient_ReturnsDrugReservations() {
		 
		 Patient patientTest = new Patient();
		 
		 DrugReservation drugReservation1 = new DrugReservation();
		 DrugReservation drugReservation2 = new DrugReservation();
		 DrugReservation drugReservation3 = new DrugReservation();
		 DrugReservation drugReservation4 = new DrugReservation();
		 DrugReservation drugReservation5 = new DrugReservation();
		 
		 List<DrugReservation> drugReservationsForPatientTest = new ArrayList<DrugReservation>();
		 
		 drugReservationsForPatientTest.add(drugReservation1);
		 drugReservationsForPatientTest.add(drugReservation2);
		 drugReservationsForPatientTest.add(drugReservation3);
		 drugReservationsForPatientTest.add(drugReservation4);
		 drugReservationsForPatientTest.add(drugReservation5);
		 
		 Mockito.when(patientRepositoryMocked.findById(2)).thenReturn(patientTest);
		 Mockito.when(drugReservationRepositoryMocked.findAllByPatientId(Constants.PATIENT_ID2)).thenReturn(drugReservationsForPatientTest);
		 List<DrugReservation> drugReservationsForPatient = drugReservationService.getReservationsForPatient();
		 assertEquals(drugReservationsForPatient.size(),5);
	 }
	 
	 
	 
}
