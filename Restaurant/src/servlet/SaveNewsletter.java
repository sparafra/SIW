package servlet;

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

import database.DBConnection;
import database.NewsletterDaoJDBC;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.Newsletter;
import model.Order;
import model.Product;
import model.Restaurant;
import model.State;
import model.User;



public class SaveNewsletter extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Mail = req.getParameter("Mail");
				
				DBConnection dbConnection = new DBConnection(); 
				NewsletterDaoJDBC NewsletterDao = new NewsletterDaoJDBC(dbConnection);
				
				Newsletter newsletter = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				Restaurant Rest = null;

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					newsletter = new Newsletter();
					
					newsletter.setMail(Mail);
					newsletter.setIdLocale(Rest.getId());

					NewsletterDao.save(newsletter);
						
					String Message = "Registrazione alla newsletter effettuata con successo! \r\n" + "Mail: " + newsletter.getMail();
						
					Email mail = new Email();
					mail.Send(newsletter.getMail(), "Registrazione alla newsletter effettuata!", Message);
						
					resp.getWriter().write("Ok");
					
				}
				else
				{
				
					resp.getWriter().write("Error");
				}
			
				
				
				
				

			
		
	}
}
