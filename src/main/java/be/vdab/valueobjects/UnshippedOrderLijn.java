package be.vdab.valueobjects;

import java.time.LocalDate;

import be.vdab.enums.Status;

public class UnshippedOrderLijn {
	private final long id;
	private final LocalDate orderDate;
	private final LocalDate requiredDate;
	private final String customerNaam;
	private final String comments;
	private final Status status;
	
	public UnshippedOrderLijn(long id, LocalDate orderDate, LocalDate requiredDate,
		String customerNaam, String comments, Status status) {
		this.id = id;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.customerNaam = customerNaam;
		this.comments = comments;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	public String getCustomerNaam() {
		return customerNaam;
	}

	public String getComments() {
		return comments;
	}

	public Status getStatus() {
		return status;
	}
	
	// niet nodig
	
}
