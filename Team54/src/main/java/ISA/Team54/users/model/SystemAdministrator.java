package ISA.Team54.users.model;

import javax.persistence.Entity;

@Entity
public class SystemAdministrator extends User {

	public SystemAdministrator() {
		super();
	}

	public SystemAdministrator(long id, String email, String password, String name, String surname, String address,
			String city, String country, String phoneNumber) {
		super(id, email, password, name, surname, address, city, country, phoneNumber);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

}
