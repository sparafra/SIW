package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.ReviewRestaurant;

public interface ReviewRestaurantDAOInterface {
	public void save(ReviewRestaurant review_restaurant);  // Create
	public ReviewRestaurant findByPrimaryKey(Long id);     // Retrieve
	public List<ReviewRestaurant> findAll();       
	public void update(ReviewRestaurant review_restaurant); //Update
	public void delete(ReviewRestaurant review_restaurant); //Delete	
}
