package serviceHibernate;

import java.util.List;

import databaseHibernate.ReviewProductDao;
import modelHibernate.ReviewProduct;

public class ReviewProductService {
	
	private static ReviewProductDao reviewproductDao;
	
	public ReviewProductService()
	{
		reviewproductDao = new ReviewProductDao();
	}
	
	public void persist(ReviewProduct entity) {
		reviewproductDao.openCurrentSessionwithTransaction();
		reviewproductDao.persist(entity);
		reviewproductDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(ReviewProduct entity) {
    	reviewproductDao.openCurrentSessionwithTransaction();
    	reviewproductDao.update(entity);
    	reviewproductDao.closeCurrentSessionwithTransaction();
    }
 
    public ReviewProduct findById(Long id) {
    	reviewproductDao.openCurrentSession();
    	ReviewProduct reviewproduct = reviewproductDao.findByPrimaryKey(id);
    	reviewproductDao.closeCurrentSession();
        return reviewproduct;
    }
 
    public void delete(Long id) {
    	reviewproductDao.openCurrentSessionwithTransaction();
    	ReviewProduct reviewproduct = reviewproductDao.findByPrimaryKey(id);
    	reviewproductDao.delete(reviewproduct);
    	reviewproductDao.closeCurrentSessionwithTransaction();
    }
 
    public List<ReviewProduct> findAll() {
    	reviewproductDao.openCurrentSession();
        List<ReviewProduct> reviewproducts = reviewproductDao.findAll();
        reviewproductDao.closeCurrentSession();
        return reviewproducts;
    }
 
    public ReviewProductDao reviewproductDao() {
        return reviewproductDao;
    }
}
