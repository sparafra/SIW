package proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DBConnection;
import database.OrderDaoJDBC;
import database.PersistenceException;
import database.ProductDaoJDBC;
import model.Order;
import model.Product;

public class OrderProxy extends Order {

    DBConnection dbConnection;
    public OrderProxy(DBConnection dbConnection)
    { 
    	this.dbConnection = dbConnection;
    }


    
    public List<Product> getListProducts() {
    	ArrayList<Product> products = new ArrayList<Product>();
		Connection connection = this.dbConnection.getConnection();
		try {
			PreparedStatement statement;
			String query = "select idProdotto from prodottiordini where idOrdine = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				ProductDaoJDBC ProductDao = new ProductDaoJDBC(dbConnection);
				Product product = ProductDao.findByPrimaryKey(Long.valueOf(result.getString("idProdotto")));
				products.add(product);
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
		this.setListProducts(products);
		return super.getListProducts(); 
    	
    }

    


    
    
}
