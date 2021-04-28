package servlet;
 
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import database.DBConnection;
import database.OrderDaoJDBC;
import model.Cart;
import model.Email;
import model.Order;
import model.Restaurant;
import model.State;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
 *
 * @author pushcommit
 */
public class AcceptPaymentRequest extends HttpServlet {
 
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	
	String Costo;
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");// Set response content type
        String title = "Stripe payment";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
 
        try {
 
            Stripe.apiKey = getServletContext().getInitParameter("stripeSecretKey");
            Map<String, String[]> httpParameters = request.getParameterMap();
 
            //esctract request parameters
            Map<String, Object> userBillingData = new HashMap<>();
            String userEmail = httpParameters.get("stripeEmail")[0];
            userBillingData.put("email", userEmail);
            userBillingData.put("stripeToken", httpParameters.get("stripeToken")[0]);

            Map<String, Object> params = new HashMap<>();
            params.put("amount", request.getParameter("Costo")); // or get from request
            params.put("currency", "eur");  // or get from request
            params.put("source", userBillingData.get("stripeToken"));// or get from request
            params.put("receipt_email", userEmail);
 
            Charge charge;
 
            charge = Charge.create(params);
 
            String chargeID = charge.getId();
            
            /*
            out.println(docType
                    + "<html>\n"
                    + "<head><title>" + title + "</title></head>\n"
                    + "<body bgcolor = \"#f0f0f0\">\n"
                    + "<h1 align = \"center\">" + title + "</h1>\n"
                    + "- PAYMENT OK. chargeID " + chargeID + " -"
                    + "</body></html>"
            );
            */
            response.sendRedirect("http://localhost:8080/Restaurant/MyAccount.html");
 
        } catch (Exception ex) {
            out.println(docType
                    + "<html>\n"
                    + "<head><title>Errore</title></head>\n"
                    + "<body bgcolor = \"#f0f0f0\">\n"
                    + "<h1 align = \"center\">" + title + "</h1>\n"
                    + "- ERROR - "
                    + "</body></html>"
            );
            out.close();
        }
    }
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	
        processRequest(req, resp);
    }
 
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	
    	Boolean Asporto = Boolean.valueOf(req.getParameter("Asporto"));
		Boolean Pagato = Boolean.valueOf(req.getParameter("Pagato"));
		Costo =req.getParameter("Costo");
		//System.out.println(Costo);
		
		DBConnection dbConnection = new DBConnection(); 
		OrderDaoJDBC OrderDao = new OrderDaoJDBC(dbConnection);
		
		User user = null;
		Cart cart = null;
		Restaurant rest = null;
		Order order = null;
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession(false);
		if(session != null)
		{
			user = (User)session.getAttribute("UserLogged");
			cart = (Cart)session.getAttribute("Cart");
			rest = (Restaurant)session.getAttribute("Restaurant");
			
			Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
            String dateStr = date.format(currentTime);
            String timeStr = time.format(currentTime);
			
			order = new Order();
			
			order.setNumeroTelefono(user.getNumeroTelefono());
			order.setAsporto(Asporto);
			order.setDateTime(currentTime);
			order.setListProducts(cart.getListProducts());
			order.setStato(State.RICHIESTO.displayName());
			order.setIdLocale(rest.getId());
			order.setPagato(Pagato);
			
			OrderDao.save(order);
			
			String Message = "Ordine effettuato con successo! \r\n" + "ID: " + order.getId().toString() +"\r\n"+ "Controlla lo stato: http://localhost:8080/Restaurant/MyAccount.html";
			
			Email mail = new Email();
			mail.Send(user.getMail(), "Ordine effettuato!", Message);
			
			session.setAttribute("Cart", new Cart());
			resp.getWriter().write("Ok");
		}
		else
		{
			resp.getWriter().write("Error");
		}
    	
    	processRequest(req, resp);
    }
 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
 
}