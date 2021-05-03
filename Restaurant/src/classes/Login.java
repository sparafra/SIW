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

import modelHibernate.Restaurant;
import modelHibernate.User;
import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;
import utils.PasswordUtil;
import modelHibernate.Error;
import modelHibernate.Order;


public class Login extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Mail = req.getParameter("Mail");
				String Password = req.getParameter("Password");
				
				Long Local = null;
				try {
					Local = Long.valueOf(req.getParameter("idLocal"));
				}
				catch(Exception e) {}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(true);

				if(Local != null)
				{
					
					RestaurantService restaurant_service = new RestaurantService();
					UserService user_service = new UserService();
					
					Restaurant restaurant = restaurant_service.findById(Local);
					List<User> users = restaurant.getListUsers();
					
					User user = user_service.findByMail(Mail);
					
					String salt = PasswordUtil.generateSalt(512).get();

					
					if(!users.contains(user))
					{
						resp.getWriter().write(Error.NOT_FOUNDED.toString());
					}
					else if(PasswordUtil.verifyPassword(Password, user.getPassword(), salt) && user.isApproved())
					{
											
						session.setAttribute("UserLogged", user);
						session.setAttribute("Cart", new Order());
						session.setAttribute("Restaurant", restaurant);
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Logged");
	
						resp.getWriter().write(obj.toString());
					}
					else if(!user.isApproved())
					{
						resp.getWriter().write(Error.NOT_APPROVED.toString());	
					}
					else
					{
						resp.getWriter().write(Error.WRONG_PASSWORD.toString());	
					}
				}
				else
				{
					Restaurant Rest = null;
					if(session != null)
					{
						Rest = (Restaurant)session.getAttribute("Restaurant");
						RestaurantService restaurant_service = new RestaurantService();
						UserService user_service = new UserService();

						Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
						
						String salt = PasswordUtil.generateSalt(512).get();

						if(restaurant_session != null)
						{
							
							List<User> users = restaurant_session.getListUsers();
							
							User user = user_service.findByMail(Mail);
							if(!users.contains(user))
							{
								resp.getWriter().write(Error.NOT_FOUNDED.toString());
							}
							else if(PasswordUtil.verifyPassword(Password, user.getPassword(), salt) && user.isApproved())
							{
													
								session.setAttribute("UserLogged", user);
								session.setAttribute("Cart", new Order());
								session.setAttribute("Restaurant", restaurant_session);
								JSONObject obj = new JSONObject();
								obj.put("Stato", "Logged");
			
								resp.getWriter().write(obj.toString());
							}
							else if(!user.isApproved())
							{
								resp.getWriter().write(Error.NOT_APPROVED.toString());	
							}
							else
							{
								resp.getWriter().write(Error.WRONG_PASSWORD.toString());	
							}
						}
						else
						{
							resp.getWriter().write(Error.GENERIC_ERROR.toString());	
						}
					}
				}
		
	}
}
