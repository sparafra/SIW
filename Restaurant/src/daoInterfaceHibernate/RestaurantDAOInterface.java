package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Restaurant;

public interface RestaurantDAOInterface {
	public void save(Restaurant restaurant);  // Create
	public Restaurant findByPrimaryKey(Long id);     // Retrieve
	public List<Restaurant> findAll();       
	public void update(Restaurant restaurant); //Update
	public void delete(Restaurant restaurant); //Delete	
}
