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



public class AddLocalSession extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idLocale = Long.valueOf(req.getParameter("id"));

				DBConnection dbConnection = new DBConnection(); 
				RestaurantDaoJDBC RestDao = new RestaurantDaoJDBC(dbConnection);
				Restaurant Rest = RestDao.findByPrimaryKeyJoin(idLocale);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(Rest == null)
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Locale Non Trovato");
					resp.getWriter().write(obj.toString());
				}
				else
				{
					HttpSession session = req.getSession(true);
					session.setAttribute("Restaurant", Rest);
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Ok");

					resp.getWriter().write(obj.toString());
				}
				
				
			
		
	}
}
