package ISA.Team54.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.users.model.Patient;
import ISA.Team54.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail( String email );
}