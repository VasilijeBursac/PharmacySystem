package ISA.Team54.users.dto;

import java.util.List;

public class DrugNamesAndQuantitiesDTO {

	private List<String> drugNames;
	private List<Integer> drugQuantities;
	
	public DrugNamesAndQuantitiesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugNamesAndQuantitiesDTO(List<String> drugNames, List<Integer> drugQuantities) {
		super();
		this.drugNames = drugNames;
		this.drugQuantities = drugQuantities;
	}

	public List<String> getDrugNames() {
		return drugNames;
	}

	public void setDrugNames(List<String> drugNames) {
		this.drugNames = drugNames;
	}

	public List<Integer> getDrugQuantities() {
		return drugQuantities;
	}

	public void setDrugQuantities(List<Integer> drugQuantities) {
		this.drugQuantities = drugQuantities;
	}
	
	
	
}
