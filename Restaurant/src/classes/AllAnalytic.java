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
import database.ProductDaoJDBC;
import model.Analytic;
import model.Product;
import model.Restaurant;



public class AllAnalytic extends HttpServlet{
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
					DBConnection dbConnection = new DBConnection(); 
					AnalyticDaoJDBC AnayticDao = new AnalyticDaoJDBC(dbConnection);
					List<Analytic> analytics = AnayticDao.findAllByLocal(Rest.getId());
					
					JSONArray jArray = new JSONArray();
					
					for(int k=0; k<analytics.size(); k++)
					{
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("id", analytics.get(k).getIdView());
							obj.put("Pagina", analytics.get(k).getPagina());
							obj.put("DataOra", analytics.get(k).getDataOra());
							obj.put("NumeroTelefono", analytics.get(k).getNumeroTelefono());
							obj.put("idLoclae", analytics.get(k).getIdLocale());
							
							
							
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
