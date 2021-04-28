package serviceHibernate;

import java.util.List;

import databaseHibernate.IngredientDao;
import modelHibernate.Ingredient;

public class IngredientService {
	
	private static IngredientDao ingredientDao;
	
	public IngredientService()
	{
		ingredientDao = new IngredientDao();
	}
	
	public void persist(Ingredient entity) {
		ingredientDao.openCurrentSessionwithTransaction();
		ingredientDao.persist(entity);
		ingredientDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(Ingredient entity) {
    	ingredientDao.openCurrentSessionwithTransaction();
    	ingredientDao.update(entity);
        ingredientDao.closeCurrentSessionwithTransaction();
    }
 
    public Ingredient findById(Long id) {
    	ingredientDao.openCurrentSession();
    	Ingredient ingredient = ingredientDao.findByPrimaryKey(id);
        ingredientDao.closeCurrentSession();
        return ingredient;
    }
 
    public void delete(Long id) {
    	ingredientDao.openCurrentSessionwithTransaction();
    	Ingredient ingredient = ingredientDao.findByPrimaryKey(id);
        ingredientDao.delete(ingredient);
        ingredientDao.closeCurrentSessionwithTransaction();
    }
 
    public List<Ingredient> findAll() {
    	ingredientDao.openCurrentSession();
        List<Ingredient> ingredients = ingredientDao.findAll();
        ingredientDao.closeCurrentSession();
        return ingredients;
    }
 
    public IngredientDao ingredientDao() {
        return ingredientDao;
    }
}
