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
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.ReviewProductDaoJDBC;
import database.UserDaoJDBC;
import model.Analytic;
import model.Cart;
import model.Email;
import model.Order;
import model.Product;
import model.Restaurant;
import model.ReviewProduct;
import model.State;
import model.User;



public class SaveReviewProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idProdotto = Long.valueOf(req.getParameter("idProdotto"));
				int Voto = Integer.valueOf(req.getParameter("Voto"));
				
				System.out.println("TEST");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				ReviewProductDaoJDBC RevProductDao = new ReviewProductDaoJDBC(dbConnection);
				
				User user = null;
				ReviewProduct rev;
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					
					rev = new ReviewProduct();
					rev.setIdProduct(idProdotto);
					rev.setNumeroTelefono(user.getNumeroTelefono());
					rev.setVoto(Voto);
					
					Date currentTime = Calendar.getInstance().getTime();
					rev.setDataOra(currentTime);
					
					RevProductDao.save(rev);
					
					
					resp.getWriter().write("Ok");
				}
				
					
				String Message = "Recensione al prodotto inviata correttamente";
					
				Email mail = new Email();
				mail.Send(user.getMail(), "Recensione Inviata!", Message);
					
			
				
				
				
				

			
		
	}
}
