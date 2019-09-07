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

import database.AnalyticDaoJDBC;
import database.DBConnection;
import database.IngredientDaoJDBC;
import database.ProductDaoJDBC;
import model.Analytic;
import model.Ingredient;
import model.Product;
import model.Restaurant;



public class AllIngredients extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idLocal = (long) 1;
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				IngredientDaoJDBC IngredientDao = new IngredientDaoJDBC(dbConnection);
				List<Ingredient> ingredients = IngredientDao.findAll(idLocal);
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<ingredients.size(); k++)
				{
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("idIngrediente", ingredients.get(k).getId());
						obj.put("Nome", ingredients.get(k).getNome());
						obj.put("Costo", ingredients.get(k).getPrezzo());
						
						
						
						jArray.put(obj);
					}catch(Exception e) {e.printStackTrace();}
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());					
			
				
		
	}
}
