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

import database.AnalyticDaoJDBC;
import database.DBConnection;
import database.LogDaoJDBC;
import database.ProductDaoJDBC;
import model.Analytic;
import model.Log;
import model.Product;
import model.Restaurant;



public class AllLogs extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Restaurant Rest = null;
				HttpSession session = req.getSession(false);
				if(session != null)
					Rest = (Restaurant)session.getAttribute("Restaurant");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(Rest != null)
				{
					DBConnection dbConnection = DBConnection.getInstance(); 
					LogDaoJDBC LogDao = new LogDaoJDBC(dbConnection);
					List<Log> logs = LogDao.findAllByLocal(Rest.getId());
					
					JSONArray jArray = new JSONArray();
					
					for(int k=0; k<logs.size(); k++)
					{
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("id", logs.get(k).getIdLog());
							obj.put("Evento", logs.get(k).getEvento());
							obj.put("NumeroTelefono", logs.get(k).getNumeroTelefono());
							obj.put("DataOra", logs.get(k).getDataOra());
							obj.put("idLocale", logs.get(k).getIdLocale());
							
							
							
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					}
					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(jArray.toString());					
					
		
				}
				else
				{
					resp.getWriter().write("error");	
				}
		
		
				
				
				
				
				
		
	}
}
