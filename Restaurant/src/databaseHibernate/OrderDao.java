package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.OrderDAOInterface;
import modelHibernate.Order;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class OrderDao implements OrderDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public OrderDao() {	}

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
    
	public void persist(Order order)
	{
		getCurrentSession().save(order);
	}
    public void update(Order order)
	{
		getCurrentSession().update(order);
	}
    
	public void delete(Order order)
	{
		getCurrentSession().delete(order);
	}
    
	public Order findByPrimaryKey(Long id)
	{
		Order order =  (Order) getCurrentSession().get(Order.class, id);
		return order;
	}
	public List<Order> findByState(String state)
	{
		List<Order> orders = (List<Order>) getCurrentSession().createQuery("from Order where state = "+state).list();
		return orders;
	}
    @SuppressWarnings("unchecked")
	public List<Order> findAll()
	{
		List<Order> orders = (List<Order>) getCurrentSession().createQuery("from Order").list();

		return orders;
		
	}
    


}
