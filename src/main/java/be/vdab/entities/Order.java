package be.vdab.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;

import be.vdab.enums.Status;

@Entity
@Table(name="orders")
@NamedEntityGraphs ({
	@NamedEntityGraph(name=Order.MET_CUSTOMER,
		attributeNodes = @NamedAttributeNode("customer")),
/*	@NamedEntityGraph(name=Order.MET_ORDERDETAILS,
		attributeNodes = @NamedAttributeNode(value = "orderDetails"))*/
})
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MET_CUSTOMER = "Order.metCustomer";
	public static final String MET_ORDERDETAILS = "Order.metOrderdetails";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate orderDate;
	private LocalDate requiredDate;
	private LocalDate shippedDate;
	private String comments;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customerId")
	private Customer customer;
	@Enumerated(EnumType.STRING)
	private Status status;
	private int version;
//	@ElementCollection
//	@CollectionTable(name = "orderdetails",
//		joinColumns = @JoinColumn(name="orderId"))
//	private Set<OrderDetail> orderDetails;
//	@ManyToMany
//	@JoinTable(
//		name="orderdetails",
//		joinColumns = @JoinColumn (name="orderId"),
//		inverseJoinColumns = @JoinColumn(name="productId"))
//	private Set<Product> products;

	public Order (LocalDate orderDate, LocalDate requiredDate, Customer customer, 
			Status status, int version) {
			this.orderDate = orderDate;
			this.requiredDate = requiredDate;
			this.customer = customer;
			this.status = status;
			this.version = version;
//			orderDetails = new LinkedHashSet<>();
//			products = new LinkedHashSet<>();
		}
	
	public Order (LocalDate orderDate, LocalDate requiredDate, LocalDate shippedDate,
		String comments, Customer customer, Status status, int version) {
		this(orderDate, requiredDate, customer, status, version);
		this.shippedDate = shippedDate;
		this.comments = comments;		
	}
	
	protected Order() {}
	
	public LocalDate getShippedDate() {
		return shippedDate;
	}	

	public Status getStatus() {
		return status;
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

	public String getComments() {
		return comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getVersion() {
		return version;
	}
	
//	public Set<OrderDetail> getOrderDetails() {
//		return Collections.unmodifiableSet(orderDetails);
//	}
//	
//	public Set<Product> getProducts() {
//		return Collections.unmodifiableSet(products);
//	}
	
	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
//	public BigDecimal getTotalValue() {
//		return orderDetails.stream().map(detail -> detail.getValue()).
//			reduce(BigDecimal.ZERO, (vorigeSom, value) -> vorigeSom.add(value));
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((requiredDate == null) ? 0 : requiredDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (requiredDate == null) {
			if (other.requiredDate != null)
				return false;
		} else if (!requiredDate.equals(other.requiredDate))
			return false;
		return true;
	}	
}
