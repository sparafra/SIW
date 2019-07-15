package proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import database.IngredientDaoJDBC;
import database.PersistenceException;
import database.ProductDaoJDBC;
import database.ReviewProductDaoJDBC;
import model.Ingredient;
import model.Product;
import model.ReviewProduct;

public class ProductProxy extends Product {
	
	DBConnection dbConnection;
	public ProductProxy(DBConnection dbConnection)
	{
    	this.dbConnection = dbConnection;
		
	}
	public List<Ingredient> getListIngredienti()
	{
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		Connection connection = this.dbConnection.getConnection();
		try {
			PreparedStatement statement;
			String query = "select idIngrediente from prodottiingredienti where idProdotto = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				IngredientDaoJDBC IngredienteDao = new IngredientDaoJDBC(dbConnection);
				Ingredient ingredient = IngredienteDao.findByPrimaryKey(Long.valueOf(result.getString("idIngrediente")));
				ingredients.add(ingredient);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		this.setListIngredienti(ingredients);
		return super.getListIngredienti(); 
		
	}
	public List<ReviewProduct> getListReview()
	{
		ArrayList<ReviewProduct> reviews = new ArrayList<ReviewProduct>();
		Connection connection = this.dbConnection.getConnection();
		try {
			PreparedStatement statement;
			String query = "select NumeroTelefono from recensioneprodottoutente where idProdotto = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				ReviewProductDaoJDBC ReviewDao = new ReviewProductDaoJDBC(dbConnection);
				ReviewProduct review = ReviewDao.findByPrimaryKeyJoin(this.getId(), result.getString("NumeroTelefono"));
				reviews.add(review);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		this.setListReview(reviews);
		return super.getListReview(); 
	}
}
