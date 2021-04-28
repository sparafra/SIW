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
import model.State;
import model.User;



public class UpdateUser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				String NumeroTelefono =req.getParameter("NumeroTelefono");
				System.out.println(NumeroTelefono);
				String Nome = req.getParameter("Nome");
				String Cognome = req.getParameter("Cognome");
				String Mail = req.getParameter("Mail");
				String Indirizzo = req.getParameter("Indirizzo");;
				String Amministratore = req.getParameter("Amministratore");;
				String Confermato = req.getParameter("Confermato");
				String Disabilitato = req.getParameter("Disabilitato");

				

				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				
				User user = UserDao.findByPrimaryKeyJoin(NumeroTelefono);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				user.setNome(Nome);
				user.setAmministratore(Boolean.valueOf(Amministratore));
				user.setCognome(Cognome);
				user.setConfermato(Boolean.valueOf(Confermato));
				user.setIndirizzo(Indirizzo);
				user.setMail(Mail);
				user.setDisabilitato(Boolean.valueOf(Disabilitato));
				
				UserDao.update(user);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
