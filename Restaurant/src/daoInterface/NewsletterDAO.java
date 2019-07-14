package daoInterface;

import java.util.List;

import model.Newsletter;


public interface NewsletterDAO {
	public void save(Newsletter N);  // Create
	public Newsletter findByPrimaryKey(String mail);     // Retrieve
	public List<Newsletter> findAll();       
	public void update(Newsletter corso); //Update
	public void delete(Newsletter corso); //Delete	
}
