package daoInterface;

import java.util.List;

import model.Product;


public interface ProductDAO {
	public void save(Product R);  // Create
	public Product findByPrimaryKey(Long codice);     // Retrieve
	public List<Product> findByTypeJoin(String Tipo);
	public List<Product> findAll();       
	public void update(Product corso); //Update
	public void delete(Product corso); //Delete	
}
