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

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBConnection;
import database.ProductDaoJDBC;
import database.RestaurantDaoJDBC;
import model.Product;
import model.Restaurant;



public class AllLocals extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				DBConnection dbConnection = new DBConnection(); 
				RestaurantDaoJDBC restDao = new RestaurantDaoJDBC(dbConnection);
				List<Restaurant> locals = restDao.findAll();
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<locals.size(); k++)
				{
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("id", locals.get(k).getId());
						obj.put("Name", locals.get(k).getName());
						obj.put("Address", locals.get(k).getAddress());
						obj.put("Mail", locals.get(k).getMail());
						obj.put("Telephone", locals.get(k).getTelephone());
						obj.put("LogoURL", locals.get(k).getLogoURL());
						obj.put("BackgroundURL", locals.get(k).getBackgroundURL());
						obj.put("Active", locals.get(k).getActive());

						
						jArray.put(obj);
					}catch(Exception e) {e.printStackTrace();}
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
				
				
				
		
	}
}
