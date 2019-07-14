package classes;

import java.io.IOException;
import java.io.PrintWriter;
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
import database.UserDaoJDBC;
import model.User;



public class isLogged extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				User user = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
					user = (User)session.getAttribute("UserLogged");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user != null)
				{
										
					JSONObject obj = new JSONObject();
					obj.put("NumeroTelefono", user.getNumeroTelefono());
					obj.put("Nome", user.getNome());
					obj.put("Cognome", user.getCognome());
					obj.put("Mail", user.getMail());
					obj.put("Indirizzo", user.getIndirizzo());
					obj.put("Password", user.getPassword());
					obj.put("Amministratore", user.getAmministratore());
					obj.put("Confermato", user.getConfermato());
					obj.put("idLocale", user.getIdLocale());

					resp.getWriter().write(obj.toString());
				}
				else
				{
					resp.getWriter().write("Not Logged");	
				}
				
				
				
				
				
		
	}
}
