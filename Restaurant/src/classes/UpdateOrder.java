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


import modelHibernate.Order;
import serviceHibernate.OrderService;
import modelHibernate.Error;




public class UpdateOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idOrder = Long.valueOf(req.getParameter("idOrdine"));

				String Stato = req.getParameter("Stato");
				Boolean Asporto = Boolean.valueOf(req.getParameter("Asporto"));
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				Boolean Pagato = Boolean.valueOf(req.getParameter("Pagato"));
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");

				
				OrderService order_service = new OrderService();
				
				Order order = order_service.findById(idOrder);
				
				
				
				order.setState(Stato);
				order.setTake_away(Asporto);
				order.setPaid(Pagato);

				if(Costo != order.getTotaleCosto())
				{
					order.setPrice(Costo);

				}
				order_service.update(order);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write(Error.COMPLETED.toString());
			
				
				
				
				

			
		
	}
}
