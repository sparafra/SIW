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

				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				Restaurant Rest = null ;
				User user = null;
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					
					
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				
				user = new User();
				
				user.setNome(Nome);
				user.setCognome(Cognome);
				user.setNumeroTelefono(NumeroTelefono);
				user.setPassword(Password);
				user.setMail(Mail);
				user.setIndirizzo(Indirizzo);
				user.setAmministratore(Amministratore);
				user.setConfermato(Confermato);
				user.setDisabilitato(false);
				user.setIdLocale(Rest.getId());
				UserDao.save(user);
					
				String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				Email mail = new Email();
				mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
