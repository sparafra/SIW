package serviceHibernate;

import java.util.List;

import databaseHibernate.RestaurantDao2;
import modelHibernate.Restaurant;

public class RestaurantService {
	
	private static RestaurantDao2 restaurantDao;
	
	public RestaurantService()
	{
		restaurantDao = new RestaurantDao2();
	}
	
	public void persist(Restaurant entity) {
		restaurantDao.openCurrentSessionwithTransaction();
		restaurantDao.persist(entity);
		restaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(Restaurant entity) {
    	restaurantDao.openCurrentSessionwithTransaction();
    	restaurantDao.update(entity);
    	restaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public Restaurant findById(Long id) {
    	restaurantDao.openCurrentSession();
    	Restaurant restaurant = restaurantDao.findByPrimaryKey(id);
    	restaurantDao.closeCurrentSession();
        return restaurant;
    }
 
    public void delete(Long id) {
    	restaurantDao.openCurrentSessionwithTransaction();
    	Restaurant restaurant = restaurantDao.findByPrimaryKey(id);
    	restaurantDao.delete(restaurant);
    	restaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public List<Restaurant> findAll() {
    	restaurantDao.openCurrentSession();
        List<Restaurant> restaurants = restaurantDao.findAll();
        restaurantDao.closeCurrentSession();
        return restaurants;
    }
    public List<Restaurant> getAllUsers() {
    	//restaurantDao.openCurrentSession();
        List<Restaurant> restaurants = restaurantDao.getAllRest();
        //restaurantDao.closeCurrentSession();
        return restaurants;
    }
 
    public RestaurantDao2 restaurantDao() {
        return restaurantDao;
    }
}
