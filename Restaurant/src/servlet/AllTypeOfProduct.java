package servlet;

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
import database.IngredientDaoJDBC;
import database.ProductDaoJDBC;
import database.TypeDaoJDBC;
import model.Analytic;
import model.Ingredient;
import model.Product;
import model.Restaurant;
import model.Type;



public class AllTypeOfProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				try {
					DBConnection dbConnection = new DBConnection(); 
					TypeDaoJDBC TypeDao = new TypeDaoJDBC(dbConnection);
					List<Type> types = TypeDao.findAll();
					
					JSONArray jArray = new JSONArray();
					
					for(int k=0; k<types.size(); k++)
					{
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("Tipo", types.get(k).getType());
							
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					}
					
					resp.setContentType("text/plain");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(jArray.toString());					
					
		
				}
				catch(Exception e)
				{
					resp.getWriter().write("error");	
				}
		
		
				
				
				
				
				
		
	}
}
