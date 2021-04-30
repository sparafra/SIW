package classes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelHibernate.User;
import modelHibernate.Error;
import serviceHibernate.UserService;



public class ConfermaUtente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Mail = req.getParameter("Mail");
				String NumeroTelefono = req.getParameter("NumeroTelefono");

				UserService user_service = new UserService();
				
				User user = user_service.findById(NumeroTelefono);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user == null)
				{
					resp.getWriter().write(Error.NOT_FOUNDED.toString());
				}
				else if(user.getMail().equals(Mail) && user.getTelephone().equals(NumeroTelefono) && !user.isApproved())
				{
					user.setApproved(true);
					user_service.update(user);
					
					resp.getWriter().write(Error.COMPLETED.toString());
				}
				else if(user.isApproved())
				{
					resp.getWriter().write(Error.COMPLETED.toString());	
				}
				else
				{
					resp.getWriter().write(Error.GENERIC_ERROR.toString());	
				}
				
			
		
	}
}
