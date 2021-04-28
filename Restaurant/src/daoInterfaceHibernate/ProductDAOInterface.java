package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Product;

public interface ProductDAOInterface {
	public void persist(Product product);  // Create
	public Product findByPrimaryKey(Long id);     // Retrieve
	public List<Product> findAll();       
	public void update(Product product); //Update
	public void delete(Product product); //Delete	
}
