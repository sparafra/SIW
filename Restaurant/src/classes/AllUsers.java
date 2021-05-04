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
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Product;
import model.Restaurant;
import model.User;



public class AllUsers extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant Rest = null;
				User user = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					user = (User)session.getAttribute("UserLogged");
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(Rest != null)
				{
					DBConnection dbConnection = DBConnection.getInstance(); 
					UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
					List<User> users = UserDao.findAllByLocal(Rest.getId());
					
					JSONArray jArray = new JSONArray();
					
					for(int k=0; k<users.size(); k++)
					{
						if(!user.equals(users.get(k)))
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
								obj.put("idLocale", users.get(k).getIdLocale());
								
								jArray.put(obj);
							}catch(Exception e) {e.printStackTrace();}
						
						}
					}
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(jArray.toString());					
					
		
				}
				else
				{
					resp.getWriter().write("error");	
				}
		
		
				
				
				
				
				
		
	}
}
