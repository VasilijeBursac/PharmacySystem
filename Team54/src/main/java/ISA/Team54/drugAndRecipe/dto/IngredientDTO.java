package ISA.Team54.drugAndRecipe.dto;

public class IngredientDTO {
	
	private long id;
	private String name;
	
	public IngredientDTO() {
		super();
	}
	
	public IngredientDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public IngredientDTO(String name) {
		super();
		this.name = name;
	}
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ingredient
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param ingredient the ingredient to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
