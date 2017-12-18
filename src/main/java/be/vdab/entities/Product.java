package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import be.vdab.valueobjects.OrderDetail;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name; 
	private String scale;
	private String description;
	private int quantityInStock;
	private int quantityInOrder;
	private BigDecimal buyPrice;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "productlineId")
	private ProductLine productLine;
	private int version;
	@ElementCollection
	@CollectionTable(name = "orderdetails",
		joinColumns = @JoinColumn(name="productId"))
	private Set<OrderDetail> orderDetails;
	@ManyToMany(mappedBy = "products")
	private Set<Order> orders;
	
	public Product () {}
	
	public Product(String name, String scale, int quantityInStock, int quantityInOrder,
		BigDecimal buyPrice, ProductLine productLine, int version) {
		this.name = name;
		this.scale = scale;
		this.quantityInStock = quantityInStock;
		this.quantityInOrder = quantityInOrder;
		this.buyPrice = buyPrice;
		this.productLine = productLine;
		this.version = version;
		orderDetails = new LinkedHashSet<>();
		orders = new LinkedHashSet<>();
	}
	
	public Product(String name, String scale, String description, int quantityInStock, 
			int quantityInOrder, BigDecimal buyPrice, ProductLine productLine, int version) {
		this(name, scale, quantityInStock, quantityInOrder, buyPrice, productLine, version);
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getScale() {
		return scale;
	}

	public String getDescription() {
		return description;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public int getQuantityInOrder() {
		return quantityInOrder;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public ProductLine getProductLine() {
		return productLine;
	}

	public int getVersion() {
		return version;
	}

	public Set<OrderDetail> getOrderDetails() {
		return Collections.unmodifiableSet(orderDetails);
	}
	
	public Set<Order> getOrders() {
		return Collections.unmodifiableSet(orders);
	}
	
	public boolean verlaagQuantities(int quantityOrdered) {
		if (quantityInOrder - quantityOrdered >=0 && quantityInStock - quantityOrdered >=0 ) {
			quantityInOrder -=  quantityOrdered;
			quantityInStock -=  quantityOrdered;
			return true;
		}
		else {
			return false;
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
