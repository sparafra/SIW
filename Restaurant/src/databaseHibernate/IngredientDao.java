package databaseHibernate;


import java.util.List;

import daoInterfaceHibernate.IngredientDAOInterface;
import modelHibernate.Ingredient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class IngredientDao implements IngredientDAOInterface {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	
	public IngredientDao() {	}

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
    
	public void persist(Ingredient ingredient)
	{
		getCurrentSession().persist(ingredient);
	}
    public void update(Ingredient ingredient)
	{
		getCurrentSession().update(ingredient);
	}
    
	public void delete(Ingredient ingredient)
	{
		getCurrentSession().delete(ingredient);
	}
    
	public Ingredient findByPrimaryKey(Long id)
	{
		Ingredient ingredient =  (Ingredient) getCurrentSession().get(Ingredient.class, id);
		return ingredient;
	}
    @SuppressWarnings("unchecked")
	public List<Ingredient> findAll()
	{
		List<Ingredient> ingredients = (List<Ingredient>) getCurrentSession().createQuery("from Ingredient").list();

		return ingredients;
		
	}


}
