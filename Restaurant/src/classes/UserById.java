package classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelHibernate.Restaurant;
import modelHibernate.User;
import serviceHibernate.UserService;
import modelHibernate.Error;


public class UserById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
		
				String NumeroTelefono = req.getParameter("NumeroTelefono");

				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				Restaurant Rest = null;
				User userLogged = null;
				
				UserService user_service = new UserService();
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					userLogged = (User)session.getAttribute("UserLogged");
					
					if(Rest != null && userLogged != null && userLogged.isAdmin())
					{
						User user = user_service.findById(NumeroTelefono);
						resp.getWriter().write(user.getJson().toString());					
					}
					else
					{
						resp.getWriter().write(Error.GENERIC_ERROR.toString());	
					}
				}
				resp.getWriter().write(Error.BLANK_SESSION.toString());	
		
		
	}
}
