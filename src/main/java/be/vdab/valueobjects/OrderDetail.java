package be.vdab.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int quantityOrdered;
	private BigDecimal priceEach;
	
	public OrderDetail(int quantityOrdered, BigDecimal priceEach) {
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
	}
	
	protected OrderDetail() {}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public BigDecimal getPriceEach() {
		return priceEach;
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
