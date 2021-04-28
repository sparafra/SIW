package serviceHibernate;

import java.util.List;

import databaseHibernate.TypeDao;
import modelHibernate.Type;

public class TypeService {
	
	private static TypeDao typeDao;
	
	public TypeService()
	{
		typeDao = new TypeDao();
	}
	
	public void persist(Type entity) {
		typeDao.openCurrentSessionwithTransaction();
		typeDao.persist(entity);
		typeDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(Type entity) {
    	typeDao.openCurrentSessionwithTransaction();
    	typeDao.update(entity);
    	typeDao.closeCurrentSessionwithTransaction();
    }
 
    public Type findById(Long id) {
    	typeDao.openCurrentSession();
    	Type restaurant = typeDao.findByPrimaryKey(id);
    	typeDao.closeCurrentSession();
        return restaurant;
    }
 
    public void delete(Long id) {
    	typeDao.openCurrentSessionwithTransaction();
    	Type restaurant = typeDao.findByPrimaryKey(id);
    	typeDao.delete(restaurant);
    	typeDao.closeCurrentSessionwithTransaction();
    }
 
    public List<Type> findAll() {
    	typeDao.openCurrentSession();
        List<Type> restaurants = typeDao.findAll();
        typeDao.closeCurrentSession();
        return restaurants;
    }
 
    public TypeDao typeDao() {
        return typeDao;
    }
}
