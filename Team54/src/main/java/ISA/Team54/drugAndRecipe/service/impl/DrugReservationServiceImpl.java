package ISA.Team54.drugAndRecipe.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ISA.Team54.drugAndRecipe.service.interfaces.IClock;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.service.interfaces.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.repository.DrugRepository;
import ISA.Team54.drugAndRecipe.repository.DrugReservationRepository;
import ISA.Team54.drugAndRecipe.repository.DrugsInPharmacyRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugReservationService;
import ISA.Team54.exceptions.InvalidTimeLeft;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.Pharmacist;
import ISA.Team54.users.model.Pharmacy;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.PharmacistRepository;
import ISA.Team54.users.repository.PharmacyRepository;

import java.util.ArrayList;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

	@Autowired
	private DrugReservationRepository drugReservationRepository;

	@Autowired
	private DrugsInPharmacyRepository drugInPharmacyRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DrugRepository drugRepository;

	@Autowired
	private PharmacistRepository pharmacistRepository;

	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PenaltyService penaltyService;

	@Override
	public void reserveDrug(DrugInPharmacyId drugInPharmacyId, Date deadline) {
		DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findOneByDrugInPharmacyId(drugInPharmacyId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		DrugReservation reservation = new DrugReservation();
		reservation.setReservedDrug(drugInPharmacy);
		reservation.setReservationToDate(deadline);
		reservation.setPatient(patient);
		reservation.setStatus(ReservationStatus.Reserved);

		DrugReservation drugReservation =  drugReservationRepository.save(reservation);
		drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - 1);
		drugInPharmacyRepository.save(drugInPharmacy);

		emailService.sendEmail("tim54isa@gmail.com","Zakazana rezervacija leka","Uspesno ste rezervisali lek." +
				" Broj Vaše rezervacije s kojim ćete preuzeti lek je: " + drugReservation.getId());
	}

	@Override
	public List<DrugReservation> getReservationsForPatient() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		return drugReservationRepository.findAllByPatientId(patient.getId());
	}

	@Override
	public void cancelDrugReservation(long id) throws Exception {
		IClock clock = new ClockImpl();
		DrugReservation drugReservation = drugReservationRepository.findById(id).orElse(null);
		if (drugReservation != null) {
			if (drugReservation.getReservationToDate().getTime() - clock.getDate().getTime() > 24 * 60 * 60 * 1000) {
				drugReservation.setStatus(ReservationStatus.Canceled);
				drugReservationRepository.save(drugReservation);
			} else throw new InvalidTimeLeft();
		} else throw new Exception();
	}

	private boolean hasExpired(DrugReservation drugReservation) {
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		if (drugReservation.getReservationToDate().compareTo(tomorrow) < 0)
			return true;
		return false;
	}

	public void sellDrug(long drugReservationId) {
		DrugReservation drugReservation = drugReservationRepository.findOneById(drugReservationId);
		DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findOneByDrugInPharmacyId(drugReservation.getReservedDrug().getDrugInPharmacyId());
		drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - 1);
		drugReservation.setStatus(ReservationStatus.Sold);
		drugReservationRepository.save(drugReservation);
	}

	private List<DrugReservation> getSoldReservationsForPatient(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		return drugReservationRepository.findAllByPatientIdAndStatus(patient.getId(), ReservationStatus.Sold );
	}

	@Override
	public List<Pharmacy> getPatientPharmacies() {
		List<DrugReservation> drugReservations = getSoldReservationsForPatient();
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		for (DrugReservation drugReservation : drugReservations) {
			long pharmacyId = drugReservation.getReservedDrug().getDrugInPharmacyId().getPharmaciId();
			pharmacies.add(pharmacyRepository.findById(pharmacyId));
		}

		return pharmacies;
	}

	@Override
	public void penalIfDeadlineOver() {
		List<DrugReservation> reservations = drugReservationRepository.getPassedReservations(ReservationStatus.Reserved);
		for (DrugReservation reservation : reservations) {
			reservation.setStatus(ReservationStatus.NotTaken);
			drugReservationRepository.save(reservation);
			penaltyService.penalPatient(reservation.getPatient());
		}
	}

	public Drug isDrugReservationAvailable(long reservationId) throws InvalidTimeLeft {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
			Pharmacist pharmacist = pharmacistRepository.findOneById(((Pharmacist) authentication.getPrincipal()).getId());
			DrugReservation drugReservation = drugReservationRepository.findOneByIdAndReservedDrugDrugInPharmacyIdPharmaciIdAndStatus(reservationId, pharmacist.getPharmacy().getId(), ReservationStatus.Reserved);
			if (drugReservation == null) {
				return null;
			}
			if (hasExpired(drugReservation)) {
				throw new InvalidTimeLeft();
			}
			//sellDrug(drugReservation.getId());
			return drugRepository.findOneById(drugReservation.getReservedDrug().getDrugInPharmacyId().getDrugId());
		
	}
}