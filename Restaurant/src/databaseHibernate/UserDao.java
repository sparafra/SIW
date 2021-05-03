package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.UserDAOInterface;
import modelHibernate.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class UserDao implements UserDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public UserDao() {	}

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
    
	public void persist(User user)
	{
		getCurrentSession().save(user);
	}
    public void update(User user)
	{
		getCurrentSession().update(user);
	}
    
	public void delete(User user)
	{
		getCurrentSession().delete(user);
	}
    
	public User findByPrimaryKey(String telephone)
	{
		User user =  (User) getCurrentSession().get(User.class, telephone);
		return user;
	}
    @SuppressWarnings("unchecked")
	public List<User> findAll()
	{
		List<User> users = (List<User>) getCurrentSession().createQuery("from User").list();

		return users;
		
	}
    public User findByMail(String mail)
	{
		User user =  (User) getCurrentSession().createQuery("from User where mail=" + mail).list().get(0);
		return user;
	}

}
