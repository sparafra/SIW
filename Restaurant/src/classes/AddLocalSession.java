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
import serviceHibernate.RestaurantService;



public class AddLocalSession extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long id = Long.valueOf(req.getParameter("id"));

				RestaurantService restaurant_service = new RestaurantService();
				
				Restaurant restaurant = restaurant_service.findById(id);

				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(restaurant == null)
				{
					resp.getWriter().write(Error.NOT_FOUNDED.toString());
				}
				else
				{
					HttpSession session = req.getSession(true);
					session.setAttribute("Restaurant", restaurant);

					resp.getWriter().write(Error.COMPLETED.toString());
				}
				
				
			
		
	}
}
