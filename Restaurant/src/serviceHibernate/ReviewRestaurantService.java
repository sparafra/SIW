package serviceHibernate;

import java.util.List;

import databaseHibernate.ReviewRestaurantDao;
import modelHibernate.ReviewRestaurant;

public class ReviewRestaurantService {
	
	private static ReviewRestaurantDao reviewrestaurantDao;
	
	public ReviewRestaurantService()
	{
		reviewrestaurantDao = new ReviewRestaurantDao();
	}
	
	public void persist(ReviewRestaurant entity) {
		reviewrestaurantDao.openCurrentSessionwithTransaction();
		reviewrestaurantDao.persist(entity);
		reviewrestaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(ReviewRestaurant entity) {
    	reviewrestaurantDao.openCurrentSessionwithTransaction();
    	reviewrestaurantDao.update(entity);
    	reviewrestaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public ReviewRestaurant findById(Long id) {
    	reviewrestaurantDao.openCurrentSession();
    	ReviewRestaurant reviewrestaurant = reviewrestaurantDao.findByPrimaryKey(id);
    	reviewrestaurantDao.closeCurrentSession();
        return reviewrestaurant;
    }
 
    public void delete(Long id) {
    	reviewrestaurantDao.openCurrentSessionwithTransaction();
    	ReviewRestaurant reviewrestaurant = reviewrestaurantDao.findByPrimaryKey(id);
    	reviewrestaurantDao.delete(reviewrestaurant);
    	reviewrestaurantDao.closeCurrentSessionwithTransaction();
    }
 
    public List<ReviewRestaurant> findAll() {
    	reviewrestaurantDao.openCurrentSession();
        List<ReviewRestaurant> reviewrestaurants = reviewrestaurantDao.findAll();
        reviewrestaurantDao.closeCurrentSession();
        return reviewrestaurants;
    }
 
    public ReviewRestaurantDao reviewrestaurantDao() {
        return reviewrestaurantDao;
    }
}
