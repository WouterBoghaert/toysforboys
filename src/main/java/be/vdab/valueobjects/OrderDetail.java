package be.vdab.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.vdab.entities.Order;
import be.vdab.entities.Product;

@Embeddable
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int quantityOrdered;
	private BigDecimal priceEach;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="orderId")
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="productId")
	private Product product;
	
	public OrderDetail(int quantityOrdered, BigDecimal priceEach, 
			Order order, Product product) {
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
		this.order = order;
		this.product = product;
	}
	
	protected OrderDetail() {}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public BigDecimal getPriceEach() {
		return priceEach;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public BigDecimal getValue() {
		return priceEach.multiply(BigDecimal.valueOf(quantityOrdered));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priceEach == null) ? 0 : priceEach.hashCode());
		result = prime * result + quantityOrdered;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderDetail))
			return false;
		OrderDetail other = (OrderDetail) obj;
		if (priceEach == null) {
			if (other.priceEach != null)
				return false;
		} else if (!priceEach.equals(other.priceEach))
			return false;
		if (quantityOrdered != other.quantityOrdered)
			return false;
		return true;
	}
	
	
}
