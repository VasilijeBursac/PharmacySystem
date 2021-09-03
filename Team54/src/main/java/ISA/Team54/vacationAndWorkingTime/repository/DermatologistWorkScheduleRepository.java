package ISA.Team54.vacationAndWorkingTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ISA.Team54.vacationAndWorkingTime.model.DermatologistWorkSchedule;

public interface DermatologistWorkScheduleRepository extends JpaRepository<DermatologistWorkSchedule, Long>{
	public List<DermatologistWorkSchedule> findByDermatologistIdAndPharmacyId(Long dermatologistId,Long pharmacyId);
	
	@Query("select dws from DermatologistWorkSchedule dws where dws.dermatologist.id = ?1 and dws.pharmacy.id = ?2")
	public DermatologistWorkSchedule getByDermatologistIdPharmacyId(Long dermatologistId,Long pharmacyId);

	@Modifying
	@Query("delete from DermatologistWorkSchedule dws where dws.dermatologist.id = ?1 and dws.pharmacy.id = ?2")
	void deleteByDermatologistIdAndPharmacyId(long dermatologistId, long pharmacyId);
}
