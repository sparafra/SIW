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
import model.Restaurant;
import model.State;
import model.User;



public class SaveNotice extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String CreatoDa = req.getParameter("CreatoDa");
				String Messaggio = req.getParameter("Messaggio");
				String RicevutoDa = req.getParameter("RicevutoDa");				
				Boolean Stato = Boolean.valueOf(req.getParameter("Stato"));
				Long idLocale = Long.valueOf(req.getParameter("idLocale"));
				String Tipo = req.getParameter("Tipo");				
				String Titolo = req.getParameter("Titolo");				

				
				DBConnection dbConnection = new DBConnection(); 
				NoticeDaoJDBC NoticeDao = new NoticeDaoJDBC(dbConnection);
				Notice notice = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				
				notice = new Notice();
				
				notice.setCreatoDa(CreatoDa);
				notice.setMessaggio(Messaggio);
				notice.setRicevutoDa(RicevutoDa);
				notice.setStato(Stato);
				notice.setIdLocale(idLocale);
				notice.setTipo(Tipo);
				notice.setTitolo(Titolo);
				NoticeDao.save(notice);
					
				
					
				resp.getWriter().write("Ok");
		
			
		
	}
}
