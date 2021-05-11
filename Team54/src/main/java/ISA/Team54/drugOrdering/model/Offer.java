package ISA.Team54.drugOrdering.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ISA.Team54.drugOrdering.enums.OfferStatus;
import ISA.Team54.users.model.Supplier;

@Entity
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = false)
	private double totalPrice;
	
	@Column(unique = false)
	private Date deliveryDeadline;
	
	@Enumerated(EnumType.STRING)
	private OfferStatus status;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private DrugsOrder order;

	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Offer(long id, double totalPrice, Date deliveryDeadline, OfferStatus status, Supplier supplier,
			DrugsOrder order) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.deliveryDeadline = deliveryDeadline;
		this.status = status;
		this.supplier = supplier;
		this.order = order;
	}
	
	public Offer(double totalPrice, Date deliveryDeadline) {
		super();
		this.totalPrice = totalPrice;
		this.deliveryDeadline = deliveryDeadline;
		this.status = OfferStatus.Waiting;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public DrugsOrder getOrder() {
		return order;
	}

	public void setOrder(DrugsOrder order) {
		this.order = order;
	}
	
	
	
}
