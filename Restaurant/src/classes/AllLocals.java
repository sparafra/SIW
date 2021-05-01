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

import modelHibernate.Restaurant;
import serviceHibernate.RestaurantService;



public class AllLocals extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				RestaurantService restaurant_service = new RestaurantService();
						
				List<Restaurant> restaurants = restaurant_service.findAll();
				
				JSONArray jArray = new JSONArray();
				
				for(Restaurant R: restaurants)
				{
					jArray.put(R.getJson());
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
				
				
				
		
	}
}
