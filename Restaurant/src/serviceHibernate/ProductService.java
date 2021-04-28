package serviceHibernate;

import java.util.List;

import databaseHibernate.ProductDao;
import modelHibernate.Product;

public class ProductService {
	
	private static ProductDao productDao;
	
	public ProductService()
	{
		productDao = new ProductDao();
	}
	
	public void persist(Product entity) {
		productDao.openCurrentSessionwithTransaction();
		productDao.persist(entity);
		productDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(Product entity) {
    	productDao.openCurrentSessionwithTransaction();
    	productDao.update(entity);
    	productDao.closeCurrentSessionwithTransaction();
    }
 
    public Product findById(Long id) {
    	productDao.openCurrentSession();
    	Product product = productDao.findByPrimaryKey(id);
    	productDao.closeCurrentSession();
        return product;
    }
 
    public void delete(Long id) {
    	productDao.openCurrentSessionwithTransaction();
    	Product product = productDao.findByPrimaryKey(id);
    	productDao.delete(product);
    	productDao.closeCurrentSessionwithTransaction();
    }
 
    public List<Product> findAll() {
    	productDao.openCurrentSession();
        List<Product> products = productDao.findAll();
        productDao.closeCurrentSession();
        return products;
    }
 
    public ProductDao productDao() {
        return productDao;
    }
}
