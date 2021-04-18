package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.ProductDAOInterface;
import modelHibernate.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class ProductDAO implements ProductDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public ProductDAO() {	}

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
    
	public void save(Product product)
	{
		getCurrentSession().save(product);
	}
    public void update(Product product)
	{
		getCurrentSession().update(product);
	}
    
	public void delete(Product product)
	{
		getCurrentSession().delete(product);
	}
    
	public Product findByPrimaryKey(Long id)
	{
		Product product =  (Product) getCurrentSession().get(Product.class, id);
		return product;
	}
    @SuppressWarnings("unchecked")
	public List<Product> findAll()
	{
		List<Product> products = (List<Product>) getCurrentSession().createQuery("from Product").list();

		return products;
		
	}


}
