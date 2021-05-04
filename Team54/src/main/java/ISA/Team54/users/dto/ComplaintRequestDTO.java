package ISA.Team54.users.dto;

import ISA.Team54.users.enums.ComplaintType;

public class ComplaintRequestDTO {

	private int objectId;
	private String text;
	private ComplaintType type;
	
	public ComplaintRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplaintRequestDTO(int objectId, String text, ComplaintType type) {
		super();
		this.objectId = objectId;
		this.text = text;
		this.type = type;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ComplaintType getType() {
		return type;
	}

	public void setType(ComplaintType type) {
		this.type = type;
	}

	
}
