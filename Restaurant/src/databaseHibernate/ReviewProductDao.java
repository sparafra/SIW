package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.ReviewProductDAOInterface;
import modelHibernate.ReviewProduct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class ReviewProductDao implements ReviewProductDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public ReviewProductDao() {	}

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
    
	public void persist(ReviewProduct review_product)
	{
		getCurrentSession().save(review_product);
	}
    public void update(ReviewProduct review_product)
	{
		getCurrentSession().update(review_product);
	}
    
	public void delete(ReviewProduct review_product)
	{
		getCurrentSession().delete(review_product);
	}
    
	public ReviewProduct findByPrimaryKey(Long id)
	{
		ReviewProduct review_product =  (ReviewProduct) getCurrentSession().get(ReviewProduct.class, id);
		return review_product;
	}
    @SuppressWarnings("unchecked")
	public List<ReviewProduct> findAll()
	{
		List<ReviewProduct> review_products = (List<ReviewProduct>) getCurrentSession().createQuery("from ReviewProduct").list();

		return review_products;
		
	}


}
