package app;

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
import model.State;
import model.User;



public class UpdateOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idOrder = Long.valueOf(req.getParameter("idOrdine"));

				String Stato = req.getParameter("Stato");
				Boolean Asporto = Boolean.valueOf(req.getParameter("Asporto"));
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				Boolean Pagato = Boolean.valueOf(req.getParameter("Pagato"));
				
				

				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrderDao = new OrderDaoJDBC(dbConnection);
				
				Order order = OrderDao.findByPrimaryKeyJoin(idOrder);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				order.setStato(Stato);
				order.setAsporto(Asporto);
				order.setPagato(Pagato);
				System.out.println(Costo);
				System.out.println(order.getTotaleCosto());

				if(Costo != order.getTotaleCosto())
				{
					order.setCosto(Costo);

				}
				OrderDao.update(order);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
