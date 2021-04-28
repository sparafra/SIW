package serviceHibernate;

import java.util.List;

import databaseHibernate.UserDao;
import modelHibernate.User;

public class UserService {
	
	private static UserDao userDao;
	
	public UserService()
	{
		userDao = new UserDao();
	}
	
	public void persist(User entity) {
		userDao.openCurrentSessionwithTransaction();
		userDao.persist(entity);
		userDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(User entity) {
    	userDao.openCurrentSessionwithTransaction();
    	userDao.update(entity);
    	userDao.closeCurrentSessionwithTransaction();
    }
 
    public User findById(String id) {
    	userDao.openCurrentSession();
    	User user = userDao.findByPrimaryKey(id);
    	userDao.closeCurrentSession();
        return user;
    }
 
    public void delete(String id) {
    	userDao.openCurrentSessionwithTransaction();
    	User user = userDao.findByPrimaryKey(id);
    	userDao.delete(user);
    	userDao.closeCurrentSessionwithTransaction();
    }
 
    public List<User> findAll() {
    	userDao.openCurrentSession();
        List<User> users = userDao.findAll();
        userDao.closeCurrentSession();
        return users;
    }
 
    public UserDao userDao() {
        return userDao;
    }
}
