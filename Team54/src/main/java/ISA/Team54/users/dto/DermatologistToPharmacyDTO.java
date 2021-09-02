package ISA.Team54.users.dto;

import java.util.Date;

public class DermatologistToPharmacyDTO {
	private long dermatologistId;
	private Date startDate;
	private Date endDate;
	
	
	public DermatologistToPharmacyDTO() {
		super();
	}

	public DermatologistToPharmacyDTO(long dermatologistId, Date startDate, Date endDate) {
		super();
		this.dermatologistId = dermatologistId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(long dermatologistId) {
		this.dermatologistId = dermatologistId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
