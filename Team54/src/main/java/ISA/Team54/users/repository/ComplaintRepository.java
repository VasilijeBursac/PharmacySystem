package ISA.Team54.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.users.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long>{

}
