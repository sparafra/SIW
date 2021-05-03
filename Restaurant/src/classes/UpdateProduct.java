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

import modelHibernate.Error;
import modelHibernate.Product;
import modelHibernate.Type;
import serviceHibernate.ProductService;
import serviceHibernate.TypeService;



public class UpdateProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idProdotto = Long.valueOf(req.getParameter("idProdotto"));

				String Nome = req.getParameter("Nome");
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				String Tipo = req.getParameter("Tipo");
				String ImageURL = req.getParameter("ImageURL");;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				TypeService type_service = new TypeService();
				Type type = type_service.findByName(Tipo);
				
				ProductService product_service = new ProductService();
				Product product = product_service.findById(idProdotto);
				
				
				product.setName(Nome);
				product.setPrice(Costo);
				product.getListTypes().add(type);
				
				if(!ImageURL.equals("null"))
					product.setImage_url(ImageURL);

				product_service.update(product);
				
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write(Error.COMPLETED.toString());
			
				
				
				
				

			
		
	}
}
