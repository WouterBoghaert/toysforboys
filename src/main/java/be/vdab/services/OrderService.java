package be.vdab.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import be.vdab.entities.Order;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.repositories.OrderRepository;
import be.vdab.valueobjects.OrderDetail;

public class OrderService extends AbstractService {
	private final OrderRepository orderRepository = new OrderRepository();
	
	public Optional<Order> read(long id) {
		return orderRepository.read(id);
	}
	
	public List<Order> findUnshippedOrders(int vanafRij, int aantalRijen) {
		return orderRepository.findUnshippedOrders(vanafRij, aantalRijen);
	}
	
	public List<Integer> setAsShipped(List<Long> ids) {
		List<Integer> failedIds = new LinkedList<>();
		beginTransaction();
		try {
			List<Order> orderLijst = orderRepository.findByIdIn(ids);
			for (Order order : orderLijst) {
				for (OrderDetail orderDetail : order.getOrderDetails()) {					
					if(orderDetail.getProduct().verlaagQuantities(orderDetail.getQuantityOrdered())) {
						// verderschrijven met zaken onder 1.5 en checken of dit op iets slaat
						// ook hoofdstuk 27 eens goed checken en kijken of order en product nu
						// goed zijn met link naar valueobject
					}
				}
			}
		
		}
		catch(RollbackException ex) {
			if(ex.getCause() instanceof OptimisticLockException) {
				throw new RecordAangepastException();
			}
		}
		catch(PersistenceException ex) {
			rollback();
			throw ex;
		}
	}
}
