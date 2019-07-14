package daoInterface;

import java.util.List;

import model.Restaurant;

public interface RestaurantDAO {
	public void save(Restaurant R);  // Create
	public Restaurant findByPrimaryKey(Long codice);     // Retrieve
	public List<Restaurant> findAll();       
	public void update(Restaurant corso); //Update
	public void delete(Restaurant corso); //Delete	
}
