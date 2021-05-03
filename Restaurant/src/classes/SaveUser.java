package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


import modelHibernate.Restaurant;
import modelHibernate.ReviewProduct;
import modelHibernate.ReviewRestaurant;
import modelHibernate.User;
import serviceHibernate.RestaurantService;
import modelHibernate.Email;
import modelHibernate.Error;
import modelHibernate.Log;
import modelHibernate.Order;



public class SaveUser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nome = req.getParameter("Nome");
				String Cognome = req.getParameter("Cognome");
				String NumeroTelefono = req.getParameter("NumeroTelefono");
				String Password = req.getParameter("Password");
				String Mail = req.getParameter("Mail");
				String Indirizzo = req.getParameter("Indirizzo");
				Boolean Amministratore = Boolean.valueOf(req.getParameter("Amministratore"));
				Boolean Confermato = Boolean.valueOf(req.getParameter("Confermato"));

				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
					RestaurantService restaurant_service = new RestaurantService();
					Restaurant restaurant_session = restaurant_service.findById(Rest.getId());
					
					User user = new User();
					
					user.setName(Nome);
					user.setSurname(Cognome);
					user.setTelephone(NumeroTelefono);
					user.setPassword(Password);
					user.setMail(Mail);
					user.setAddress(Indirizzo);
					user.setAdmin(Amministratore);
					user.setApproved(Confermato);
					user.setDisabled(false);
					user.setListLogs(new ArrayList<Log>());
					user.setListOrders(new ArrayList<Order>());
					ArrayList<Restaurant> list = new ArrayList<Restaurant>();
					list.add(restaurant_session);
					user.setListRestaurants(list);
					user.setListReviewProduct(new ArrayList<ReviewProduct>());
					user.setListReviewRestaurant(new ArrayList<ReviewRestaurant>());
					
					restaurant_session.getListUsers().add(user);
					restaurant_service.update(restaurant_session);
					
					String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getTelephone()+"&Mail="+user.getMail();
						
					Email mail = new Email();
					mail.Send(user.getMail(), "Registrazione effettuata!", Message);
						
					resp.getWriter().write(Error.COMPLETED.toString());
				
					
				}
				resp.getWriter().write(Error.GENERIC_ERROR.toString());

		
		
	}
}
