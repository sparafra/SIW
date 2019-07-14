package daoInterface;

import java.util.List;

import model.Analytic;
import model.Log;
import model.User;


public interface LogDAO {
	public void save(Log U);  // Create
	public User findByPrimaryKey(String NumeroTelefono);     // Retrieve
	public User findByMailJoin(String Mail);
	public User findByMailTelJoin(String Mail, String NumeroTelefono);
	public List<Log> findAll();       
	public void update(User user); //Update
	public void delete(User user); //Delete	
}
