package classes;

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



public class UpdateProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idProdotto = Long.valueOf(req.getParameter("idProdotto"));

				String Nome = req.getParameter("Nome");
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				String Tipo = req.getParameter("Tipo");
				String ImageURL = req.getParameter("ImageURL");;
				
				

				DBConnection dbConnection = DBConnection.getInstance(); 
				ProductDaoJDBC ProductDao = new ProductDaoJDBC(dbConnection);
				
				Product product = ProductDao.findByPrimaryKeyJoin(idProdotto);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				product.setNome(Nome);
				product.setPrezzo(Costo);
				product.setTipo(Tipo);
				
				if(!ImageURL.equals("null"))
					product.setImageURL(ImageURL);

				ProductDao.update(product);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
