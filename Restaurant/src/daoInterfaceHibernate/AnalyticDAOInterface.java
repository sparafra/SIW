package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Analytic;


public interface AnalyticDAOInterface {
	public void save(Analytic analytic);  // Create
	public Analytic findByPrimaryKey(Long id);     // Retrieve
	public List<Analytic> findAll();       
	public void update(Analytic analytic); //Update
	public void delete(Analytic analytic); //Delete	
}
