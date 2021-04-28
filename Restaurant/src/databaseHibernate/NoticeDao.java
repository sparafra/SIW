package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.NoticeDAOInterface;
import modelHibernate.Notice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class NoticeDao implements NoticeDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public NoticeDao() {	}

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
    
	public void persist(Notice notice)
	{
		getCurrentSession().save(notice);
	}
    public void update(Notice notice)
	{
		getCurrentSession().update(notice);
	}
    
	public void delete(Notice notice)
	{
		getCurrentSession().delete(notice);
	}
    
	public Notice findByPrimaryKey(Long id)
	{
		Notice notice =  (Notice) getCurrentSession().get(Notice.class, id);
		return notice;
	}
    @SuppressWarnings("unchecked")
	public List<Notice> findAll()
	{
		List<Notice> notices = (List<Notice>) getCurrentSession().createQuery("from Notice").list();

		return notices;
		
	}


}
