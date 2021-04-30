package classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import modelHibernate.Error;
import modelHibernate.Restaurant;


public class LocalInfo extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant Rest = null;
				HttpSession session = req.getSession(false);
				if(session != null)
					Rest = (Restaurant)session.getAttribute("Restaurant");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(Rest != null)
				{
					resp.getWriter().write(Rest.getJson().toString());
							
				}
				else
				{
					resp.getWriter().write(Error.BLANK_SESSION.toString());
				}
				
				
				
		
	}
}
