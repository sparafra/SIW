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

import database.DBConnection;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.ReviewLocalDaoJDBC;
import database.ReviewProductDaoJDBC;
import database.UserDaoJDBC;
import model.Analytic;
import model.Cart;
import model.Email;
import model.Order;
import model.Product;
import model.Restaurant;
import model.ReviewLocal;
import model.ReviewProduct;
import model.State;
import model.User;



public class SaveReviewLocal extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				int Voto = Integer.valueOf(req.getParameter("Voto"));
				String Recensione = req.getParameter("Recensione");
				System.out.println("TEST");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				ReviewLocalDaoJDBC RevLocalDao = new ReviewLocalDaoJDBC(dbConnection);
				
				User user = null;
				Restaurant Rest = null;

				ReviewLocal rev;
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");

					rev = new ReviewLocal();
					rev.setIdLocale(Rest.getId());
					rev.setNumeroTelefono(user.getNumeroTelefono());
					rev.setVoto(Voto);
					rev.setRecensione(Recensione);
					
					Date currentTime = Calendar.getInstance().getTime();
					rev.setDataOra(currentTime);
					
					RevLocalDao.save(rev);
					
					
					resp.getWriter().write("Ok");
				}
				
					
				String Message = "Recensione del locale inviata correttamente";
					
				Email mail = new Email();
				mail.Send(user.getMail(), "Recensione Inviata!", Message);
					
			
				
				
				
				

			
		
	}
}
