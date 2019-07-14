package daoInterface;

import java.util.List;

import model.Analytic;
import model.User;


public interface AnalyticDAO {
	public void save(Analytic U);  // Create
	public User findByPrimaryKey(String NumeroTelefono);     // Retrieve
	public User findByMailJoin(String Mail);
	public User findByMailTelJoin(String Mail, String NumeroTelefono);
	public List<Analytic> findAll();       
	public void update(User user); //Update
	public void delete(User user); //Delete	
}
