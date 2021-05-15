package databaseHibernate;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import daoInterfaceHibernate.RestaurantDAOInterface;
import modelHibernate.Restaurant;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class RestaurantDao2 implements RestaurantDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public RestaurantDao2() {	}

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
    
	public void persist(Restaurant restaurant)
	{
		getCurrentSession().save(restaurant);
	}
    public void update(Restaurant restaurant)
	{
		getCurrentSession().update(restaurant);
	}
    
	public void delete(Restaurant restaurant)
	{
		getCurrentSession().delete(restaurant);
	}
    
	public Restaurant findByPrimaryKey(Long id)
	{
		Restaurant restaurant =  (Restaurant) getCurrentSession().get(Restaurant.class, id);
		return restaurant;
	}
    @SuppressWarnings("unchecked")
	public List<Restaurant> findAll()
	{
		List<Restaurant> restaurants = (List<Restaurant>) getCurrentSession().createQuery("FROM modelHibernate.Restaurant", Restaurant.class).list();

		return restaurants;
		
	}

    public static EntityManager getEntityManager() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("Restaurant")
                .createEntityManager();

        return entityManager;
    }
    
    public List<Restaurant> getAllRest() {

        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        TypedQuery<Restaurant> usersQuery = entityManager.createQuery("from Restaurant", Restaurant.class);
        List<Restaurant> usersList = usersQuery.getResultList();
        transaction.commit();
        entityManager.close();

        return usersList;
    }
}
