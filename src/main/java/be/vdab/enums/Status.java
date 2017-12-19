package be.vdab.enums;

public enum Status {
	PROCESSING("Processing"), RESOLVED("Resolved"), DISPUTED("Disputed"), 
	WAITING("Waiting"), SHIPPED("Shipped"), CANCELLED("Cancelled");
	
	private final String status;
	
	Status(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
