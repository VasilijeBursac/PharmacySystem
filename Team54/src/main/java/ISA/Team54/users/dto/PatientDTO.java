package ISA.Team54.users.dto;

public class PatientDTO {
	private int id;
	private String name;
	private String surname;
	private int loyaltyPoints;
	private int penaltyPoints;
	
	
	public PatientDTO() {
		super();
	}
	
	public PatientDTO(int id, String name, String surname) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public PatientDTO(int loyaltyPoints, int penaltyPoints) {
		super();
		this.loyaltyPoints = loyaltyPoints;
		this.penaltyPoints = penaltyPoints;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public int getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(int penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}

}

	