package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.ReviewRestaurantDAOInterface;
import modelHibernate.ReviewRestaurant;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class ReviewRestaurantDAO implements ReviewRestaurantDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public ReviewRestaurantDAO() {	}

	public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
 
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
     
    public void closeCurrentSession() {
        currentSession.close();
    }
     
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
     
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
 
    public Session getCurrentSession() {
        return currentSession;
    }
    
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
 
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
 
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
    
	public void save(ReviewRestaurant review_restaurant)
	{
		getCurrentSession().save(review_restaurant);
	}
    public void update(ReviewRestaurant review_restaurant)
	{
		getCurrentSession().update(review_restaurant);
	}
    
	public void delete(ReviewRestaurant review_restaurant)
	{
		getCurrentSession().delete(review_restaurant);
	}
    
	public ReviewRestaurant findByPrimaryKey(Long id)
	{
		ReviewRestaurant review_restaurant =  (ReviewRestaurant) getCurrentSession().get(ReviewRestaurant.class, id);
		return review_restaurant;
	}
    @SuppressWarnings("unchecked")
	public List<ReviewRestaurant> findAll()
	{
		List<ReviewRestaurant> review_restaurants = (List<ReviewRestaurant>) getCurrentSession().createQuery("from ReviewRestaurant").list();

		return review_restaurants;
		
	}


}
