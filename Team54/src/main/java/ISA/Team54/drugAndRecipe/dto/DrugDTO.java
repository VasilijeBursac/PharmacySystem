package ISA.Team54.drugAndRecipe.dto;

import java.util.List;

import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugSpecification;

public class DrugDTO {
	private long id;
	private String code;
	private int loyalityPoints;
	private String name;
	private String type;
	private String shape;
	private String manifacturer;
	private String additionalInfo;
	private List<Long> substituteDrugs;
	private int drugSpecification;
	
	public DrugDTO() {
		super();
	}
	
	public DrugDTO(long id, String code, int loyalityPoints, String name, String type, String shape,
			String manifacturer, String additionalInfo) {
		super();
		this.id = id;
		this.code = code;
		this.loyalityPoints = loyalityPoints;
		this.name = name;
		this.type = type;
		this.shape = shape;
		this.manifacturer = manifacturer;
		this.additionalInfo = additionalInfo;
	}
	
	
	
	public DrugDTO(long id, String code, int loyalityPoints, String name, String type, String shape,
			String manifacturer, String additionalInfo, List<Long> substituteDrugs,
			int drugSpecification) {
		super();
		this.id = id;
		this.code = code;
		this.loyalityPoints = loyalityPoints;
		this.name = name;
		this.type = type;
		this.shape = shape;
		this.manifacturer = manifacturer;
		this.additionalInfo = additionalInfo;
		this.substituteDrugs = substituteDrugs;
		this.drugSpecification = drugSpecification;
	}

	public DrugDTO(long id,String code, int loyalityPoints, String name) {
		super();
		this.id = id;
		this.code = code;
		this.loyalityPoints = loyalityPoints;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLoyalityPoints() {
		return loyalityPoints;
	}
	public void setLoyalityPoints(int loyalityPoints) {
		this.loyalityPoints = loyalityPoints;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getManifacturer() {
		return manifacturer;
	}
	public void setManifacturer(String manifacturer) {
		this.manifacturer = manifacturer;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	/**
	 * @return the substituteDrugs
	 */

	public List<Long> getSubstituteDrugs() {
		return substituteDrugs;
	}

	public void setSubstituteDrugs(List<Long> substituteDrugs) {
		this.substituteDrugs = substituteDrugs;
	}

	public int getDrugSpecification() {
		return drugSpecification;
	}

	public void setDrugSpecification(int drugSpecification) {
		this.drugSpecification = drugSpecification;
	}
	
	
	
	
}
