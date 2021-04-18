package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.TypeDAOInterface;
import modelHibernate.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class TypeDAO implements TypeDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public TypeDAO() {	}

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
    
	public void save(Type type)
	{
		getCurrentSession().save(type);
	}
    public void update(Type type)
	{
		getCurrentSession().update(type);
	}
    
	public void delete(Type type)
	{
		getCurrentSession().delete(type);
	}
    
	public Type findByPrimaryKey(Long id)
	{
		Type type =  (Type) getCurrentSession().get(Type.class, id);
		return type;
	}
    @SuppressWarnings("unchecked")
	public List<Type> findAll()
	{
		List<Type> types = (List<Type>) getCurrentSession().createQuery("from Type").list();

		return types;
		
	}


}
