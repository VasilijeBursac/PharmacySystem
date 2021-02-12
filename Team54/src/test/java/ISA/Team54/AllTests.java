package ISA.Team54;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ISA.Team54.integration.ExaminationIntegrationTests;
import ISA.Team54.unit.DrugTests;
import ISA.Team54.unit.UserTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserTests.class,
	DrugTests.class,
	ExaminationIntegrationTests.class
})
public class AllTests {
	
}