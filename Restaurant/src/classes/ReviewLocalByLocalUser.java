package classes;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;


import modelHibernate.Restaurant;
import modelHibernate.ReviewRestaurant;
import modelHibernate.User;
import modelHibernate.Error;

import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;



public class ReviewLocalByLocalUser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	

				
				
				JSONObject obj = new JSONObject();
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					User user = (User)session.getAttribute("UserLogged");
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
					RestaurantService restaurant_service = new RestaurantService();
					UserService user_service = new UserService();
					
					
					
					if(Rest != null && user != null)
					{
						Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
						User user_session = user_service.findById(user.getTelephone());
						
						List<ReviewRestaurant> reviews = restaurant_session.getListReviewRestaurant();
						reviews.retainAll(user_session.getListReviewRestaurant());
						
						if(reviews.size() != 0)
						{
							resp.getWriter().write(reviews.get(0).getJson().toString());
						}
						else
							resp.getWriter().write(Error.NOT_FOUNDED.toString());

							
					}
				}
				resp.getWriter().write(Error.BLANK_SESSION.toString());	
		
	}
}
