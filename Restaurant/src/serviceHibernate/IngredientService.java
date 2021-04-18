package serviceHibernate;

import databaseHibernate.IngredientDao;

public class IngredientService {
	
	private static IngredientDao ingredientDao;
	
	public IngredientService()
	{
		ingredientDao = new IngredientDao();
	}
	
	
}
