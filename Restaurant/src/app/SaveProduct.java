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



public class SaveProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nome = req.getParameter("Nome");
				Float Prezzo = Float.valueOf(req.getParameter("Prezzo"));
				Long idLocale = Long.valueOf(req.getParameter("idLocale"));
				String Tipo = req.getParameter("Tipo");

				
				DBConnection dbConnection = new DBConnection(); 
				ProductDaoJDBC ProductDao = new ProductDaoJDBC(dbConnection);
				Product product = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				product = new Product();
				product.setIdLocale(idLocale);
				product.setImageURL(null);
				product.setListIngredienti(null);
				product.setListReview(null);
				product.setNome(Nome);
				product.setPrezzo(Prezzo);
				
				product.setTipo(Tipo);
				
				
				resp.getWriter().write("Ok");
		
			
		
	}
}
