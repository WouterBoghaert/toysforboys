package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import be.vdab.entities.Order;

public class OrderRepository extends AbstractRepository {
	public Optional<Order> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Order.class, id));
	}
	
	public Optional<Order> readWithCustomerAndCountry(long id) {
		try {
			return Optional.of(getEntityManager()
					.createNamedQuery("Order.findById", Order.class)
					.setParameter("id", id)
					.setHint("javax.persistence.loadgraph",
						getEntityManager().createEntityGraph(Order.MET_CUSTOMER_EN_LAND))
					.getSingleResult());
		}
		catch(NoResultException ex) {
			return Optional.empty();
		}
	}
	
	public Optional<Order> readWithOrderdetails(long id) {
		try {
			return Optional.of(getEntityManager()
					.createNamedQuery("Order.findById", Order.class)
					.setParameter("id", id)
					.setHint("javax.persistence.loadgraph",
						getEntityManager().createEntityGraph(Order.MET_ORDERDETAILS))
					.getSingleResult());
		}
		catch(NoResultException ex) {
			return Optional.empty();
		}
	}
	
	public List<Order> findUnshippedOrders(int vanafRij, int aantalRijen) {
		return getEntityManager()
			.createNamedQuery("Order.findUnshippedOrders", Order.class)
			.setFirstResult(vanafRij)
			.setMaxResults(aantalRijen)
			.setHint("javax.persistence.loadgraph", 
				getEntityManager().createEntityGraph(Order.MET_CUSTOMER))
			.getResultList();
	}
	
//	public List<Order> findByIdIn(List<Long> ids) {
//		return getEntityManager()
//			.createNamedQuery("Order.findByIdIn", Order.class)
//			.setParameter("ids", ids)
//			.setHint("javax.persistence.loadgraph", 
//				getEntityManager().createEntityGraph(Order.MET_ORDERDETAILS_EN_PRODUCT))
//			.getResultList();
//	}
}

//Checken of EntityGraphs nodig zijn, eventueel herschrijven en uitbreiden, 
// ook in orders aanpassen
