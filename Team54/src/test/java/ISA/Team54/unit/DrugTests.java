package ISA.Team54.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
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
	private DrugsInPharmacyRepository drugsInPharmacyRepositoryMock;
	
	@Autowired
	private DrugReservationService drugReservationService;
	
	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	@Autowired
	private DrugService drugService;
	
	
	@Test public void  isPatientAlergicOnDrugTest(){
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
		assertFalse(isAllergic);
	}	
	
	 @Test public void getDrugsInPharmaciesByDrug_ReturnsDrugInPharmacies() {
		 
		 DrugInPharmacyId drugInPharmacyId1 = new DrugInPharmacyId(1,1);
		 DrugInPharmacyId drugInPharmacyId2 = new DrugInPharmacyId(3,1);
		 DrugInPharmacyId drugInPharmacyId3 = new DrugInPharmacyId(2,1);
		 DrugInPharmacyId drugInPharmacyId4 = new DrugInPharmacyId(5,1);
		 DrugInPharmacyId drugInPharmacyId5 = new DrugInPharmacyId(1,3);
		 
		 DrugInPharmacy drugnInPharmacy1  = new DrugInPharmacy(drugInPharmacyId1,20);
		 DrugInPharmacy drugnInPharmacy2  = new DrugInPharmacy(drugInPharmacyId2,10);
		 DrugInPharmacy drugnInPharmacy3  = new DrugInPharmacy(drugInPharmacyId3,70);
		 DrugInPharmacy drugnInPharmacy4  = new DrugInPharmacy(drugInPharmacyId4,2);
		 DrugInPharmacy drugnInPharmacy5  = new DrugInPharmacy(drugInPharmacyId5,53);
		 
		 List<DrugInPharmacy> drugInPharmacy = new ArrayList<DrugInPharmacy>();
		 
		 drugInPharmacy.add(drugnInPharmacy1);
		 drugInPharmacy.add(drugnInPharmacy2);
		 drugInPharmacy.add(drugnInPharmacy3);
		 drugInPharmacy.add(drugnInPharmacy4);
		 drugInPharmacy.add(drugnInPharmacy5);
		 
		 Mockito.when(drugsInPharmacyRepositoryMock.findByDrugId(1L)).thenReturn(drugInPharmacy);
		 
		 List<DrugInPharmacy> drugInPharmacies = drugInPharmacyService.getDrugsInPharmaciesByDrug(Constants.DRUG_ID1);
		 assertEquals(drugInPharmacies.size(),5);
	 }
}
