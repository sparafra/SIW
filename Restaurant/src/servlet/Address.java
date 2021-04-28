package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import database.DBConnection;
import database.RestaurantDaoJDBC;
import model.Restaurant;


public class Address extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
		
				DBConnection dbConnection = new DBConnection(); 
				RestaurantDaoJDBC RestDao = new RestaurantDaoJDBC(dbConnection);
				Restaurant Rest = RestDao.findByPrimaryKeyJoin((long) 1);
		
				JSONObject obj = new JSONObject();
				try
				{
					obj.put("id", Rest.getId());
					obj.put("Name", Rest.getName());
					obj.put("Address", Rest.getAddress());
					obj.put("Mail", Rest.getMail());
					obj.put("Telephone", Rest.getTelephone());
					obj.put("LogoURL", Rest.getLogoURL());
				}catch(Exception e) {e.printStackTrace();}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());
				
				
				
				
		
	}
}
