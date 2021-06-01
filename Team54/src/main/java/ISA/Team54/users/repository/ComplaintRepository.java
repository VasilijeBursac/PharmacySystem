package ISA.Team54.users.repository;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import ISA.Team54.users.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	public Optional<Complaint> findById(Long id);
}
