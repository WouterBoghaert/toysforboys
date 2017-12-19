package be.vdab.services;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import be.vdab.entities.Order;
import be.vdab.enums.Status;
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
	
	public List<Long> setAsShipped(List<Long> ids) {
		List<Long> failedIds = new LinkedList<>();
		List<Order> orderLijst = orderRepository.findByIdIn(ids);				
		for (Order order : orderLijst) {
			try {
				boolean rollback = false;
				beginTransaction();
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					if (!orderDetail.getProduct().verlaagQuantities(orderDetail.getQuantityOrdered())) {
						rollback = true;
						failedIds.add(order.getId());
						break;
					}				
				}
				if(rollback) {
					rollback();
				}
				else {
					order.setStatus(Status.SHIPPED);
					order.setShippedDate(LocalDate.now());
					commit();
				}
			} 
			catch (RollbackException ex) {
				if (ex.getCause() instanceof OptimisticLockException) {
					throw new RecordAangepastException();
				}
			} 
			catch (PersistenceException ex) {
				rollback();
				throw ex;
			}
		}
		return failedIds;
	}
}
