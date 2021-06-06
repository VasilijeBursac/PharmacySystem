package ISA.Team54.drugAndRecipe.dto;

import java.util.Date;
import java.util.List;

public class NewERecipeDTO {

	private long pharmacyId;
	private Date dateOfIssue;
	private List<String> drugNames;
	private List<Integer> quantities;
	
	public NewERecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewERecipeDTO(long pharmacyId, Date dateOfIssue, List<String> drugNames, List<Integer> quantities) {
		super();
		this.pharmacyId = pharmacyId;
		this.dateOfIssue = dateOfIssue;
		this.drugNames = drugNames;
		this.quantities = quantities;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public List<String> getDrugNames() {
		return drugNames;
	}

	public void setDrugNames(List<String> drugNames) {
		this.drugNames = drugNames;
	}

	public List<Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	
	
}
