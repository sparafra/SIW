package classes;

import java.io.IOException;
import java.io.PrintWriter;
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
import database.IngredientDaoJDBC;
import database.OrderDaoJDBC;
import model.Ingredient;
import model.Order;
import model.Product;
import model.Restaurant;
import model.User;



public class IngredientById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idIngredient = Long.valueOf(req.getParameter("idIngrediente"));

				
				User user = null;
				Restaurant Rest = null;
				DBConnection dbConnection = new DBConnection(); 
				IngredientDaoJDBC IngredientDao = new IngredientDaoJDBC(dbConnection);
				
				JSONObject obj = new JSONObject();

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null && user != null && user.getAmministratore())
					{
						Ingredient ingredient = IngredientDao.findByPrimaryKeyJoin(idIngredient);
					
						
							try
							{
								obj.put("idIngrediente", ingredient.getId());
								obj.put("Nome", ingredient.getNome());
								obj.put("Prezzo", ingredient.getPrezzo());
								
								
								
							}catch(Exception e) {e.printStackTrace();}
						
					}
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());
				
			
		
	}
}
