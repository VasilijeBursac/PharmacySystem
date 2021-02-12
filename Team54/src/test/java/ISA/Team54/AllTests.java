package ISA.Team54;

import ISA.Team54.unit.DrugReservationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ISA.Team54.unit.DrugTests;
import ISA.Team54.unit.UserTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserTests.class,
	DrugTests.class,
		DrugReservationTests.class
})
public class AllTests {
	
}
