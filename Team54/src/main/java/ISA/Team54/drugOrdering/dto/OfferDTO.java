package ISA.Team54.drugOrdering.dto;

import java.util.Date;

import ISA.Team54.drugOrdering.enums.OfferStatus;

public class OfferDTO {
	
	private long offerId;
	private String pharmacyName;
	private Date deliveryDeadline;
	private double totalPrice;
	private long orderId;
	private String offerStatus;
	private String supplierFullName;
	
	
	public OfferDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OfferDTO(long offerId, double totalPrice, Date deliveryDeadline,String offerStatus,
			String pharmacyName, String supplierFullName, long orderId) {
		super();
		this.offerId = offerId;
		this.totalPrice = totalPrice;
		this.deliveryDeadline = deliveryDeadline;
		this.offerStatus = offerStatus;
		this.pharmacyName = pharmacyName;
		this.supplierFullName = supplierFullName;
		this.orderId = orderId;
	}

	public OfferDTO(double totalPrice, Date deliveryDeadline, long orderId) {
		super();
		this.totalPrice = totalPrice;
		this.deliveryDeadline = deliveryDeadline;
		this.orderId = orderId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(Date deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getSupplierFullName() {
		return supplierFullName;
	}

	public void setSupplierFullName(String supplierFullName) {
		this.supplierFullName = supplierFullName;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}
	
	
	
}
