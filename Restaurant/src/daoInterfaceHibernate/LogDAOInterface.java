package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Log;

public interface LogDAOInterface {
	public void save(Log log);  // Create
	public Log findByPrimaryKey(Long id);     // Retrieve
	public List<Log> findAll();       
	public void update(Log log); //Update
	public void delete(Log log); //Delete	
}
