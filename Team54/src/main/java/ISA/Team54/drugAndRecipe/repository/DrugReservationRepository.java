package ISA.Team54.drugAndRecipe.repository;

import ISA.Team54.drugAndRecipe.enums.ReservationStatus;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

@Repository
public interface DrugReservationRepository extends JpaRepository<DrugReservation, Long> {

    List<DrugReservation> findAllByPatientId(long id);
    void deleteById(long id);
    DrugReservation findOneByIdAndReservedDrugDrugInPharmacyIdPharmaciIdAndStatus(long id, long reservedDrugPharmaciId, ReservationStatus status);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    DrugReservation findOneById(long id);
    List<DrugReservation> findAllByPatientIdAndStatus(long id, ReservationStatus sold);

    @Query("select r from DrugReservation r where reservation_to_date < CURRENT_TIMESTAMP and status = ?1 ")
    List<DrugReservation> getPassedReservations(ReservationStatus status);
    
    DrugReservation findOneByPatientIdAndStatusAndReservedDrugDrugInPharmacyIdPharmaciId(long patientId, ReservationStatus sold, long pharmacyId);
    
    @Query("select r from DrugReservation r where reserved_drug_drug_id = ?1 and reserved_drug_pharmaci_id = ?2 and status = ?3")
    List<DrugReservation> getAllReservationsForDrugInPharmacyByStatus(long drugId, long pharmacyId, ReservationStatus status);
}
