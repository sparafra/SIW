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

import modelHibernate.Log;
import modelHibernate.Restaurant;
import modelHibernate.User;
import modelHibernate.Error;
import serviceHibernate.LogService;
import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;



public class SaveLog extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Evento = req.getParameter("Event");
				
				LogService logService = new LogService();
				
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					RestaurantService restaurant_service = new RestaurantService();
										
					User user = (User)session.getAttribute("UserLogged");
					Restaurant restaurant = (Restaurant)session.getAttribute("Restaurant");
					
					Restaurant restaurant_logged = restaurant_service.findById(restaurant.getId());
					
					Log log = new Log();
					log.setEvent(Evento);
					log.setDate_time(Calendar.getInstance().getTime());
					
					restaurant_logged.getListLogs().add(log);
					restaurant_service.update(restaurant_logged);
					
					if(user!=null)
					{
						UserService user_service = new UserService();
						User user_logged = user_service.findById(user.getTelephone());
						user_logged.getListLogs().add(log);
						user_service.update(user_logged);
					}
					resp.getWriter().write(Error.COMPLETED.toString());

				}
								
			
				resp.getWriter().write(Error.GENERIC_ERROR.toString());
			
		
	}
}
