package classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelHibernate.User;
import modelHibernate.Error;



public class isLogged extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				User user = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
					user = (User)session.getAttribute("UserLogged");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user != null)
				{	
					resp.getWriter().write(user.getJson().toString());
				}
				else
				{
					resp.getWriter().write(Error.BLANK_SESSION.toString());	
				}
				
				
				
				
				
		
	}
}
