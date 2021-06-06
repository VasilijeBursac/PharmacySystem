package ISA.Team54.drugAndRecipe.dto;

import java.util.Date;
import java.util.List;

public class ERecipeDTO {

	private long id;
	private Date dateOfIssue;
	private List<DrugForERecipeDTO> drugs;
	
	public ERecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ERecipeDTO(long id, Date dateOfIssue, List<DrugForERecipeDTO> drugs) {
		super();
		this.id = id;
		this.dateOfIssue = dateOfIssue;
		this.drugs = drugs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public List<DrugForERecipeDTO> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<DrugForERecipeDTO> drugs) {
		this.drugs = drugs;
	}
	
	
}
