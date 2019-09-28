package app;

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
import database.NoticeDaoJDBC;
import database.OrderDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.Notice;
import model.Order;
import model.Product;
import model.State;
import model.User;



public class UpdateNotice extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idAvviso = Long.valueOf(req.getParameter("idAvviso"));
				System.out.println(idAvviso);
				Boolean Stato = Boolean.valueOf(req.getParameter("Stato"));
				String CreatoDa = req.getParameter("CreatoDa");
				String Messaggio = req.getParameter("Messaggio");
				Long idLocale = Long.valueOf(req.getParameter("idLocale"));;
				String RicevutoDa = req.getParameter("RicevutoDa");;
				String Tipo = req.getParameter("Tipo");;
				String Titolo = req.getParameter("Titolo");				


				DBConnection dbConnection = new DBConnection(); 
				NoticeDaoJDBC NoticeDao = new NoticeDaoJDBC(dbConnection);
				
				Notice notice = NoticeDao.findByPrimaryKeyJoin(idAvviso);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				notice.setStato(Stato);
				notice.setCreatoDa(CreatoDa);
				notice.setMessaggio(Messaggio);
				notice.setIdLocale(idLocale);
				notice.setRicevutoDa(RicevutoDa);
				notice.setTipo(Tipo);
				notice.setTitolo(Titolo);

				NoticeDao.update(notice);
					
				//String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Restaurant/ConfermaUtente.html?NumeroTelefono="+user.getNumeroTelefono()+"&Mail="+user.getMail();
					
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message);
					
				resp.getWriter().write("Ok");
			
				
				
				
				

			
		
	}
}
