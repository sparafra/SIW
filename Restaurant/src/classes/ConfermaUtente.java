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
import model.Cart;
import model.User;



public class ConfermaUtente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Mail = req.getParameter("Mail");
				String NumeroTelefono = req.getParameter("NumeroTelefono");

				DBConnection dbConnection = new DBConnection(); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				User user = UserDao.findByMailTelJoin(Mail, NumeroTelefono);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user == null)
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Utente Non Trovato");
					resp.getWriter().write(obj.toString());
				}
				else if(user.getMail().equals(Mail) && user.getNumeroTelefono().equals(NumeroTelefono) && !user.getConfermato())
				{
					user.setConfermato(true);
					UserDao.update(user);
					
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Confermato");

					resp.getWriter().write(obj.toString());
				}
				else if(user.getConfermato())
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Utente già Confermato");
					resp.getWriter().write(obj.toString());	
				}
				else
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Error");
					resp.getWriter().write(obj.toString());	
				}
				
			
		
	}
}
