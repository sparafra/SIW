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


import modelHibernate.Analytic;
import model.Product;
import modelHibernate.Restaurant;
import serviceHibernate.RestaurantService;
import modelHibernate.Error;


public class AllAnalytic extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				HttpSession session = req.getSession(false);
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				if(session != null)
				{
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null)
					{
						RestaurantService restaurant_service = new RestaurantService();
						Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
						
						List<Analytic> analytics = restaurant_session.getListAnalytics();
						
						JSONArray jArray = new JSONArray();
						
						for(Analytic a: analytics)
						{
							jArray.put(a.getJson());
						}
						
						resp.getWriter().write(jArray.toString());					
						
			
					}
					else
					{
						resp.getWriter().write(Error.GENERIC_ERROR.toString());	
					}
				}
				resp.getWriter().write(Error.BLANK_SESSION.toString());	

							
	}
}
