package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import database.DBConnection;
import database.RestaurantDaoJDBC;
import model.Restaurant;


public class LocalInfo extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant Rest = null;
				HttpSession session = req.getSession(false);
				if(session != null)
					Rest = (Restaurant)session.getAttribute("Restaurant");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(Rest != null)
				{
								
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("id", Rest.getId());
						obj.put("Name", Rest.getName());
						obj.put("Address", Rest.getAddress());
						obj.put("Mail", Rest.getMail());
						obj.put("Telephone", Rest.getTelephone());
						obj.put("LogoURL", Rest.getLogoURL());
						obj.put("Active", Rest.getActive());
					}catch(Exception e) {e.printStackTrace();}
					
					resp.getWriter().write(obj.toString());
				}
				else
				{
					resp.getWriter().write("error");
				}
				
				
				
		
	}
}
