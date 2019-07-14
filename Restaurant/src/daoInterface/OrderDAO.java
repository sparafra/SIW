package daoInterface;

import java.util.List;

import model.Order;


public interface OrderDAO {
	public void save(Order O);  // Create
	public Order findByPrimaryKey(Long codice);     // Retrieve
	public List<Order> findByUserJoin(String NumeroTelefonoUser);
	public List<Order> findAll(Long idLocal);       
	public void update(Order corso); //Update
	public void delete(Order corso); //Delete	
}
