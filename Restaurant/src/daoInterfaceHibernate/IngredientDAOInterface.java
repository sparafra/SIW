package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Ingredient;

public interface IngredientDAOInterface {
	public void persist(Ingredient ingredient);  // Create
	public Ingredient findByPrimaryKey(Long id);     // Retrieve
	public List<Ingredient> findAll();       
	public void update(Ingredient ingredient); //Update
	public void delete(Ingredient ingredient); //Delete	
}
