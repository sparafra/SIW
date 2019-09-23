package app;

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
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Product;
import model.Restaurant;
import model.User;



public class UserById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String NumeroTelefono = req.getParameter("NumeroTelefono");
				System.out.println(NumeroTelefono);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				User user = UserDao.findByPrimaryKeyJoin(NumeroTelefono);
				
				JSONObject obj = new JSONObject();
				
				try
				{
					if(user != null)
					{
						obj.put("NumeroTelefono", user.getNumeroTelefono());
						obj.put("Nome", user.getNome());
						obj.put("Cognome", user.getCognome());
						obj.put("Mail", user.getMail());
						obj.put("Indirizzo", user.getIndirizzo());
						obj.put("Password", user.getPassword());
						obj.put("Amministratore", user.getAmministratore());
						obj.put("Confermato", user.getConfermato());
						obj.put("Disabilitato", user.getDisabilitato());
						obj.put("idLocale", user.getIdLocale());
						System.out.println(user.getIdLocale());
						System.out.println(obj.toString());
					}
							
				}catch(Exception e) {e.printStackTrace();}
					
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());					
					
		
				
		
		
				
				
				
				
				
		
	}
}
