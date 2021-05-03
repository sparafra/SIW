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

import modelHibernate.Error;
import modelHibernate.Restaurant;
import modelHibernate.User;
import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;



public class AllUsersByConfirm extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String confirm = req.getParameter("Confermati");
				boolean confermato = false;
				if(confirm.equals("true"))
					confermato = true;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					User user = (User)session.getAttribute("UserLogged");
				
				
					if(Rest != null)
					{
						RestaurantService restaurant_service = new RestaurantService();
						Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
						
						UserService user_service = new UserService();
						User user_session = new User();
						
						List<User> users = restaurant_session.getListUsers();
						
						JSONArray jArray = new JSONArray();
						
						for(User u: users)
						{
							if(u.isApproved() && user_session != u)
								jArray.put(u.getJson());
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
