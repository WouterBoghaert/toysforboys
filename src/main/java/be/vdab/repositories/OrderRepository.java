package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Order;

public class OrderRepository extends AbstractRepository {
	public Optional<Order> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Order.class, id));
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
	
	public List<Order> findByIdIn(List<Long> ids) {
		return getEntityManager()
			.createNamedQuery("Order.findByIdIn", Order.class)
			.setParameter("ids", ids)
			.setHint("javax.persistence.loadgraph", 
				getEntityManager().createEntityGraph(Order.MET_ORDERDETAILS))
			.getResultList();
	}
}
