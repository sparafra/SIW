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



public class AllUsers extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idLocal = (long) 1;
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				List<User> users = UserDao.findAllByLocal(idLocal);
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<users.size(); k++)
				{
					
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("NumeroTelefono", users.get(k).getNumeroTelefono());
							obj.put("Nome", users.get(k).getNome());
							obj.put("Cognome", users.get(k).getCognome());
							obj.put("Mail", users.get(k).getMail());
							obj.put("Indirizzo", users.get(k).getIndirizzo());
							obj.put("Password", users.get(k).getPassword());
							obj.put("Amministratore", users.get(k).getAmministratore());
							obj.put("Confermato", users.get(k).getConfermato());
							obj.put("Disabilitato", users.get(k).getDisabilitato());
							obj.put("idLocale", users.get(k).getIdLocale());
							
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					
					
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());					
					
		
				
		
		
				
				
				
				
				
		
	}
}
