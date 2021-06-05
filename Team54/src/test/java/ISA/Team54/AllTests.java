package ISA.Team54;

import ISA.Team54.integration.ComplaintIntegrationTests;
import ISA.Team54.integration.DrugReservationIntegrationTests;
import ISA.Team54.integration.ExaminationIntegrationTests;
import ISA.Team54.integration.LoyaltyIntegrationTests;
import ISA.Team54.integration.PatientIntegrationTests;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.unit.DrugReservationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ISA.Team54.integration.ExaminationIntegrationTests;
import ISA.Team54.unit.DrugTests;
import ISA.Team54.unit.OfferTests;
import ISA.Team54.unit.PharmacyTests;
import ISA.Team54.unit.UserTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserTests.class,
	DrugTests.class,
	PharmacyTests.class,
	OfferTests.class,
	DrugReservationTests.class,

		
		ComplaintIntegrationTests.class,
		DrugReservationIntegrationTests.class,
		LoyaltyIntegrationTests.class,
		ExaminationIntegrationTests.class,
		PatientIntegrationTests.class
})
public class AllTests {
	
}
