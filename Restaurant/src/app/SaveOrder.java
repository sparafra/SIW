package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import model.Restaurant;
import model.State;
import model.User;



public class SaveOrder extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Boolean Asporto = Boolean.valueOf(req.getParameter("Asporto"));
				Boolean Pagato = Boolean.valueOf(req.getParameter("Pagato"));
				Long idLocal = Long.valueOf(req.getParameter("idLocale"));
				String NumeroTelefono = req.getParameter("NumeroTelefono");
				float Costo = Float.valueOf(req.getParameter("Costo"));
				String DateTime = req.getParameter("DataOra");
	
				
				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrderDao = new OrderDaoJDBC(dbConnection);
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				User user = UserDao.findByPrimaryKeyJoin(NumeroTelefono);

				Order order = null;
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
/*				
					Date currentTime = Calendar.getInstance().getTime();
		            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		            String dateStr = date.format(currentTime);
		            String timeStr = time.format(currentTime);
	*/				
				Date date1 = null;
				try {
					date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(DateTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  

					order = new Order();
					
					order.setNumeroTelefono(NumeroTelefono);
					order.setAsporto(Asporto);
					order.setDateTime(date1);
					order.setListProducts(null);
					order.setStato(State.RICHIESTO.displayName());
					order.setIdLocale(idLocal);
					order.setPagato(Pagato);
					order.setCosto(Costo);
					
					OrderDao.save(order);
					
					System.out.println(order.getId());
					
					String Message = "Ordine effettuato con successo! \r\n" + "ID: " + order.getId().toString() +"\r\n"+ "Controlla lo stato: http://localhost:8080/Restaurant/MyAccount.html";
					
					/*
					Email mail = new Email();
					mail.Send(user.getMail(), "Ordine effettuato!", Message);
					*/
					resp.getWriter().write("Inserito \n" + order.getId());
			
		
	}
}
