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

import database.AnalyticDaoJDBC;
import database.DBConnection;
import database.LogDaoJDBC;
import database.NewsletterDaoJDBC;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Analytic;
import model.Cart;
import model.Email;
import model.Log;
import model.Newsletter;
import model.Order;
import model.Product;
import model.Restaurant;
import model.State;
import model.User;



public class SaveLog extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Evento = req.getParameter("Evento");
				
				DBConnection dbConnection = DBConnection.getInstance(); 
				LogDaoJDBC LogDao = new LogDaoJDBC(dbConnection);
				
				Log log = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				User user = null;
				Restaurant Rest = null;

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					log = new Log();
					
					log.setIdLocale(Rest.getId());
					if(user!=null)
						log.setNumeroTelefono(user.getNumeroTelefono());
					log.setEvento(Evento);
					Date currentTime = Calendar.getInstance().getTime();
					log.setDataOra(currentTime);
					
					LogDao.save(log);
				}
								
			
				resp.getWriter().write("Ok");
			
		
	}
}
