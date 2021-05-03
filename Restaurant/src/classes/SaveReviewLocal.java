package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import modelHibernate.Error;

import modelHibernate.Email;
import modelHibernate.Restaurant;
import modelHibernate.ReviewRestaurant;
import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;
import modelHibernate.ReviewProduct;
import modelHibernate.User;



public class SaveReviewLocal extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				int Voto = Integer.valueOf(req.getParameter("Voto"));
				String Recensione = req.getParameter("Recensione");
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
								
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					User user = (User)session.getAttribute("UserLogged");
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
					UserService user_service = UserService();
					RestaurantService restaurant_service = new RestaurantService();
					
					Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
					User user_session = user_service.findById(user.getTelephone());
					
					ReviewRestaurant rev = new ReviewRestaurant();
					rev.setUser(user_session);
					rev.setRestaurant(restaurant_session);
					rev.setVote(Voto);
					rev.setReview(Recensione);
					
					Date currentTime = Calendar.getInstance().getTime();
					rev.setDate_time(currentTime);
					
					restaurant_session.getListReviewRestaurant().add(rev);
					restaurant_service.update(restaurant_session);
					
					user_session.getListReviewRestaurant().add(rev);
					user_service.update(user_session);
					
					resp.getWriter().write(Error.COMPLETED.toString());
					
					String Message = "Recensione del locale inviata correttamente";
					
					Email mail = new Email();
					mail.Send(user_session.getMail(), "Recensione Inviata!", Message);
						
				}
				
				resp.getWriter().write(Error.BLANK_SESSION.toString());

		
		
	}
}
