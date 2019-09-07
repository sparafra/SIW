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



public class UserByMail extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idLocal = Long.valueOf(req.getParameter("idLocale"));
				String Mail = req.getParameter("Mail");
				System.out.println(Mail);
				System.out.println(idLocal);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				User user = UserDao.findByMailLocalJoin(Mail, idLocal);
				JSONArray jArray = new JSONArray();
				
				JSONObject obj = new JSONObject();
				try
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
					jArray.put(obj);
							
				}catch(Exception e) {e.printStackTrace();}
					
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());					
					
		
				
		
		
				
				
				
				
				
		
	}
}
