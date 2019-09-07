package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBConnection;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.Order;
import model.Product;
import model.Restaurant;
import model.State;
import model.User;



public class SaveProductIngredient extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				Long idIngredient = Long.valueOf(req.getParameter("idIngrediente"));
				Long idProduct = Long.valueOf(req.getParameter("idProdotto"));
					
				
				DBConnection dbConnection = new DBConnection(); 
				ProductDaoJDBC ProductDao = new ProductDaoJDBC(dbConnection);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				ProductDao.updateIngredientiProdotto(idProduct, idIngredient);
				
				
				

			
		
	}
}
