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



import modelHibernate.Restaurant;
import modelHibernate.Error;
import serviceHibernate.RestaurantService;



public class LocalBySession extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	

				RestaurantService restaurant_service = new RestaurantService();
		
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Restaurant restaurant_session = (Restaurant)session.getAttribute("Restaurant");
					
					if(restaurant_session != null )
					{
						Restaurant restaurant = restaurant_service.findById(restaurant_session.getId());
					
						resp.getWriter().write(restaurant.getJson().toString());
						
					}
				}
				
				resp.getWriter().write(Error.BLANK_SESSION.toString());
				
			
		
	}
}
