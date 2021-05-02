package classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import modelHibernate.Restaurant;
import modelHibernate.Error;


public class isRestaurantChosen extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant restaurant = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
					restaurant = (Restaurant)session.getAttribute("Restaurant");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(restaurant != null)
				{
					
					resp.getWriter().write(restaurant.getJson().toString());
				}
				else
				{
					resp.getWriter().write(Error.NOT_FOUNDED.toString());	
				}				
		
	}
}
