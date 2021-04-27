package ISA.Team54.drugAndRecipe.dto;

public class ContraindicationDTO {
	
	private long id;
	private String name;
	
	public ContraindicationDTO() {
		super();
	}

	public ContraindicationDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ContraindicationDTO(String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
