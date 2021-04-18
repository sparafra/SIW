package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.LogDAOInterface;
import modelHibernate.Log;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class LogDAO implements LogDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public LogDAO() {	}

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
    
	public void save(Log log)
	{
		getCurrentSession().save(log);
	}
    public void update(Log log)
	{
		getCurrentSession().update(log);
	}
    
	public void delete(Log log)
	{
		getCurrentSession().delete(log);
	}
    
	public Log findByPrimaryKey(Long id)
	{
		Log log =  (Log) getCurrentSession().get(Log.class, id);
		return log;
	}
    @SuppressWarnings("unchecked")
	public List<Log> findAll()
	{
		List<Log> logs = (List<Log>) getCurrentSession().createQuery("from Log").list();

		return logs;
		
	}


}
