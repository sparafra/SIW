package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Type;

public interface TypeDAOInterface {
	public void persist(Type type);  // Create
	public Type findByPrimaryKey(Long id);     // Retrieve
	public List<Type> findAll();       
	public void update(Type type); //Update
	public void delete(Type type); //Delete	
}
