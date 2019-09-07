package app;

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
import database.NoticeDaoJDBC;
import database.ProductDaoJDBC;
import database.UserDaoJDBC;
import model.Notice;
import model.Product;
import model.Restaurant;
import model.User;



public class AllNoticeByRicevutoDa extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String NumeroTelefono = req.getParameter("NumeroTelefono");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				NoticeDaoJDBC NoticeDao = new NoticeDaoJDBC(dbConnection);
				List<Notice> notices = NoticeDao.findByRicevutoDa(NumeroTelefono, false);
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<notices.size(); k++)
				{
					
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("idAvviso", notices.get(k).getIdAvviso());
							obj.put("Stato", notices.get(k).getStato());
							obj.put("CreatoDa", notices.get(k).getCreatoDa());
							obj.put("Messaggio", notices.get(k).getMessaggio());
							obj.put("idLocale", notices.get(k).getIdLocale());
							obj.put("RicevutoDa", notices.get(k).getRicevutoDa());
							
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					
					
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());					
					
		
				
		
		
				
				
				
				
				
		
	}
}
