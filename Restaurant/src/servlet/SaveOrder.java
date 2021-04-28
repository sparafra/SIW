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
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.Order;
import model.Product;
import model.Restaurant;
import model.State;
import model.User;



public class SaveOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Boolean Asporto = Boolean.valueOf(req.getParameter("Asporto"));
				Boolean Pagato = Boolean.valueOf(req.getParameter("Pagato"));

				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrderDao = new OrderDaoJDBC(dbConnection);
				
				User user = null;
				Cart cart = null;
				Restaurant rest = null;
				Order order = null;
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					cart = (Cart)session.getAttribute("Cart");
					rest = (Restaurant)session.getAttribute("Restaurant");
					
					Date currentTime = Calendar.getInstance().getTime();
		            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		            String dateStr = date.format(currentTime);
		            String timeStr = time.format(currentTime);
					
					order = new Order();
					
					order.setNumeroTelefono(user.getNumeroTelefono());
					order.setAsporto(Asporto);
					order.setDateTime(currentTime);
					order.setListProducts(cart.getListProducts());
					order.setStato(State.RICHIESTO.displayName());
					order.setIdLocale(rest.getId());
					order.setPagato(Pagato);
					
					OrderDao.save(order);
					
					String Message = "Ordine effettuato con successo! \r\n" + "ID: " + order.getId().toString() +"\r\n"+ "Controlla lo stato: http://localhost:8080/Restaurant/MyAccount.html";
					
					Email mail = new Email();
					mail.Send(user.getMail(), "Ordine effettuato!", Message);
					
					session.setAttribute("Cart", new Cart());
					resp.getWriter().write("Ok");
				}
				else
				{
					resp.getWriter().write("Error");
				}
				
				
				

			
		
	}
}
