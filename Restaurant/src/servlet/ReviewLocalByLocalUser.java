package servlet;

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
import database.ReviewLocalDaoJDBC;
import model.Ingredient;
import model.Order;
import model.Product;
import model.Restaurant;
import model.ReviewLocal;
import model.User;



public class ReviewLocalByLocalUser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	

				
				User user = null;
				Restaurant Rest = null;
				DBConnection dbConnection = new DBConnection(); 
				ReviewLocalDaoJDBC RevLocalDao = new ReviewLocalDaoJDBC(dbConnection);
				
				JSONObject obj = new JSONObject();
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null && user != null)
					{
						ReviewLocal rev = RevLocalDao.findByPrimaryKeyJoin(Rest.getId(), user.getNumeroTelefono());

							try
							{
								obj.put("idLocale", rev.getIdLocale());
								obj.put("Voto", rev.getVoto());
								obj.put("Recensione", rev.getRecensione());
								obj.put("NumeroTelefono", rev.getNumeroTelefono());
								obj.put("DataOra", rev.getDataOra());
								resp.getWriter().write(obj.toString());

								
							}catch(Exception e) {e.printStackTrace();}
						
					}
				}
				resp.getWriter().write("null");

				
			
		
	}
}
