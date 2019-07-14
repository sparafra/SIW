package daoInterface;

import java.util.List;

import model.User;


public interface UserDAO {
	public void save(User U);  // Create
	public User findByPrimaryKey(String NumeroTelefono);     // Retrieve
	public User findByMailJoin(String Mail);
	public User findByMailTelJoin(String Mail, String NumeroTelefono);
	public List<User> findAll();       
	public void update(User user); //Update
	public void delete(User user); //Delete	
}
