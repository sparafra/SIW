package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.ReviewProduct;

public interface UserDAOInterface {
	public void save(ReviewProduct review_product);  // Create
	public ReviewProduct findByPrimaryKey(Long id);     // Retrieve
	public List<ReviewProduct> findAll();       
	public void update(ReviewProduct review_product); //Update
	public void delete(ReviewProduct review_product); //Delete	
}
