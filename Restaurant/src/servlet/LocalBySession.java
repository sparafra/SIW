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
import database.OrderDaoJDBC;
import database.RestaurantDaoJDBC;
import model.Order;
import model.Product;
import model.Restaurant;
import model.User;



public class LocalBySession extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	

				Restaurant Rest = null;
				DBConnection dbConnection = new DBConnection(); 
				RestaurantDaoJDBC RestaurantDao = new RestaurantDaoJDBC(dbConnection);
				
				JSONObject obj = new JSONObject();

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null )
					{
						Restaurant restaurant = RestaurantDao.findByPrimaryKeyJoin(Rest.getId());
					
						
							try
							{
								obj.put("id", restaurant.getId());
								obj.put("Name", restaurant.getName());
								obj.put("Address", restaurant.getAddress());
								obj.put("Mail", restaurant.getMail());
								obj.put("Telephone", restaurant.getTelephone());
								obj.put("LogoURL", restaurant.getLogoURL());
								obj.put("Active", restaurant.getActive());
																		
							}catch(Exception e) {e.printStackTrace();}
						
					}
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());
				
			
		
	}
}
