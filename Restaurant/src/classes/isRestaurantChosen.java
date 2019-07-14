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
import database.UserDaoJDBC;
import model.Restaurant;
import model.User;



public class isRestaurantChosen extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant rest = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
					rest = (Restaurant)session.getAttribute("Restaurant");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(rest != null)
				{
										
					JSONObject obj = new JSONObject();
					obj.put("id", rest.getId());
					obj.put("Nome", rest.getName());
					obj.put("Indirizzo", rest.getAddress());
					obj.put("NumeroTelefono", rest.getTelephone());
					obj.put("Mail", rest.getMail());
					obj.put("logoURL", rest.getLogoURL());
					

					resp.getWriter().write(obj.toString());
				}
				else
				{
					resp.getWriter().write("No");	
				}
				
				
				
				
				
		
	}
}
