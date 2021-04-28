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
import database.IngredientDaoJDBC;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.Ingredient;
import model.Order;
import model.Product;
import model.State;
import model.User;



public class UpdateIngrediente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idIngrediente = Long.valueOf(req.getParameter("idIngrediente"));

				String Nome = req.getParameter("Nome");
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				
				System.out.println(idIngrediente);;

				DBConnection dbConnection = new DBConnection(); 
				IngredientDaoJDBC IngredientDao = new IngredientDaoJDBC(dbConnection);
				
				Ingredient ing = IngredientDao.findByPrimaryKeyJoin(idIngrediente);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				ing.setNome(Nome);
				ing.setPrezzo(Costo);
				
				
				IngredientDao.update(ing);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
