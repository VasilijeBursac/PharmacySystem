package ISA.Team54.unit;

import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.impl.ClockImpl;
import ISA.Team54.drugAndRecipe.service.interfaces.IClock;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
import ISA.Team54.exceptions.InvalidTimeLeft;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.users.repository.PharmacyRepository;
import ISA.Team54.users.service.interfaces.PenaltyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrugReservationTests {

    @MockBean
    private DrugReservationRepository drugReservationRepository;

    @MockBean
    private DrugsInPharmacyRepository drugInPharmacyRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private DrugRepository drugRepository;

    @MockBean
    private PharmacistRepository pharmacistRepository;

    @MockBean
    private PharmacyRepository pharmacyRepository;

    @Mock
    private EmailService emailService;

    @MockBean
    private PenaltyService penaltyService;

    @Autowired
    private DrugReservationService drugReservationService;

    @MockBean
    private Authentication authentication;

    @Test
    public void testGetReservationsForPatient(){
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("Filip");
        patient.setSurname("Filipovic");

        List<DrugReservation> reservations = new ArrayList<DrugReservation>();
        reservations.add(new DrugReservation(1, new Date(), ReservationStatus.Reserved, patient));
        reservations.add(new DrugReservation(2, new Date(), ReservationStatus.Reserved, patient));
        reservations.add(new DrugReservation(3, new Date(), ReservationStatus.Reserved, patient));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(patient);

        when(patientRepository.findById(1L)).thenReturn(patient);
        when(drugReservationRepository.findAllByPatientId(patient.getId())).thenReturn(reservations);
        List<DrugReservation> drugReservations = drugReservationService.getReservationsForPatient();
        assertEquals(drugReservations.size(), 3);
    }

    @Test
    public void testPenalIfDeadlineOver(){
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Filip");
        patient1.setSurname("Filipovic");

        Patient patient2 = new Patient();
        patient2.setId(2);
        patient2.setName("Filip");
        patient2.setSurname("Filipovic");

        Patient patient3 = new Patient();
        patient3.setId(3);
        patient3.setName("Filip");
        patient3.setSurname("Filipovic");

        List<DrugReservation> reservations = new ArrayList<DrugReservation>();
        reservations.add(new DrugReservation(1, new Date(), ReservationStatus.Reserved, patient1));
        reservations.add(new DrugReservation(2, new Date(), ReservationStatus.Reserved, patient2));
        reservations.add(new DrugReservation(3, new Date(), ReservationStatus.Reserved, patient3));

        when(drugReservationRepository.getPassedReservations(ReservationStatus.Reserved)).thenReturn(reservations);
        when(drugReservationRepository.save(any(DrugReservation.class))).thenReturn(null);
        doNothing().when(penaltyService).penalPatient(any(Patient.class));

        drugReservationService.penalIfDeadlineOver();

        verify(penaltyService, times(3)).penalPatient(any(Patient.class));
    }

    @Test public void testCancelDrugReservation_Ok() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("Filip");
        patient.setSurname("Filipovic");

        Date now = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, 3);
        Date reservationDate = c.getTime();

        DrugReservation drugReservation = new DrugReservation(1, reservationDate, ReservationStatus.Reserved, patient);

        IClock clock = Mockito.mock(IClock.class);
        when(clock.getDate()).thenReturn(now);

        when(drugReservationRepository.findById(1L)).thenReturn(java.util.Optional.of(drugReservation));
        when(drugReservationRepository.save(any(DrugReservation.class))).thenReturn(null);

        try{
            drugReservationService.cancelDrugReservation(1L);
        }catch (Exception ignored){}

        verify(drugReservationRepository, times(1)).save(any(DrugReservation.class));
    }

    @Test
    public void testCancelDrugReservation_Invalid(){
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("Filip");
        patient.setSurname("Filipovic");

        Date now = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MINUTE, 2);
        Date reservationDate = c.getTime();

        DrugReservation drugReservation = new DrugReservation(1, reservationDate, ReservationStatus.Reserved, patient);

        IClock clock = Mockito.mock(IClock.class);
        when(clock.getDate()).thenReturn(now);

        when(drugReservationRepository.findById(1L)).thenReturn(java.util.Optional.of(drugReservation));
        when(drugReservationRepository.save(any(DrugReservation.class))).thenReturn(null);

        try{
            drugReservationService.cancelDrugReservation(1L);
        }catch (InvalidTimeLeft ignored){

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(drugReservationRepository, times(0)).save(any(DrugReservation.class));
    }

}
