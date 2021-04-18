package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Order;

public interface OrderDAOInterface {
	public void save(Order order);  // Create
	public Order findByPrimaryKey(Long id);     // Retrieve
	public List<Order> findAll();       
	public void update(Order order); //Update
	public void delete(Order order); //Delete	
}
