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
import database.RestaurantDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Restaurant;
import model.User;



public class Login extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Mail = req.getParameter("Mail");
				String Password = req.getParameter("Password");
				Long Local = null;
				try {
					Local = Long.valueOf(req.getParameter("idLocal"));
				}
				catch(Exception e) {}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				HttpSession session = req.getSession(true);

				if(Local != null)
				{
					DBConnection dbConnection = new DBConnection(); 
					UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
					RestaurantDaoJDBC RestDao = new RestaurantDaoJDBC(dbConnection);

					User user = UserDao.findByMailLocalJoin(Mail, Local);
					Restaurant R = RestDao.findByPrimaryKeyJoin(Local);
					
					if(user == null)
					{
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Utente Non Trovato");
						resp.getWriter().write(obj.toString());
					}
					else if(user.getPassword().equals(Password) && user.getConfermato())
					{
											
						session.setAttribute("UserLogged", user);
						session.setAttribute("Cart", new Cart());
						session.setAttribute("Restaurant", R);
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Logged");
	
						resp.getWriter().write(obj.toString());
					}
					else if(user.getConfermato() == false)
					{
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Utente non Confermato");
						resp.getWriter().write(obj.toString());	
					}
					else
					{
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Password errata");
						resp.getWriter().write(obj.toString());	
					}
				}
				else
				{
					Restaurant Rest = null;
					if(session != null)
						Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null)
					{
						DBConnection dbConnection = new DBConnection(); 
						UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
						User user = UserDao.findByMailLocalJoin(Mail, Rest.getId());
						
						
						if(user == null)
						{
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Utente Non Trovato");
							resp.getWriter().write(obj.toString());
						}
						else if(user.getPassword().equals(Password) && user.getConfermato())
						{
												
							session.setAttribute("UserLogged", user);
							session.setAttribute("Cart", new Cart());
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Logged");
		
							resp.getWriter().write(obj.toString());
						}
						else if(user.getConfermato() == false)
						{
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Utente non Confermato");
							resp.getWriter().write(obj.toString());	
						}
						else
						{
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Password errata");
							resp.getWriter().write(obj.toString());	
						}
					}
					else
					{
						resp.getWriter().write("error");	
					}
				}
		
	}
}
