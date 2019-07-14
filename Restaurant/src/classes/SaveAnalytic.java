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
import database.NewsletterDaoJDBC;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Analytic;
import model.Cart;
import model.Email;
import model.Newsletter;
import model.Order;
import model.Product;
import model.Restaurant;
import model.State;
import model.User;



public class SaveAnalytic extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Pagina = req.getParameter("Pagina");
				
				DBConnection dbConnection = new DBConnection(); 
				AnalyticDaoJDBC AnalyticDao = new AnalyticDaoJDBC(dbConnection);
				
				Analytic analytic = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				User user = null;
				Restaurant Rest = null;

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					analytic = new Analytic();
					
					analytic.setIdLocale(Rest.getId());
					if(user!=null)
						analytic.setNumeroTelefono(user.getNumeroTelefono());
					analytic.setPagina(Pagina);
					Date currentTime = Calendar.getInstance().getTime();
					analytic.setDataOra(currentTime);
					
					AnalyticDao.save(analytic);
				}
								
			
				resp.getWriter().write("Ok");
			
		
	}
}
