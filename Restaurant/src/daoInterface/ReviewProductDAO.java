package daoInterface;

import java.util.List;

import model.Ingredient;
import model.ReviewProduct;

public interface ReviewProductDAO {
	public void save(ReviewProduct rev);  // Create
	public Ingredient findByPrimaryKey(Long codice);     // Retrieve
	public List<ReviewProduct> findAll();       
	public void update(ReviewProduct rev); //Update
	public void delete(ReviewProduct rev); //Delete	
}
