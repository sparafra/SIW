package daoInterface;

import java.util.List;

import model.Ingredient;

public interface IngredientDAO {
	public void save(Ingredient I);  // Create
	public Ingredient findByPrimaryKey(Long codice);     // Retrieve
	public List<Ingredient> findAll(Long idLocal);       
	public void update(Ingredient corso); //Update
	public void delete(Ingredient corso); //Delete	
}
