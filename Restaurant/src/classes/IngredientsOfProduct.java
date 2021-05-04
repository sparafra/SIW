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

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBConnection;
import database.IngredientDaoJDBC;
import model.Ingredient;



public class IngredientsOfProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String id = req.getParameter("id");
		
				DBConnection dbConnection = DBConnection.getInstance(); 
				IngredientDaoJDBC IngDao = new IngredientDaoJDBC(dbConnection);
				List<Ingredient> ingredients = IngDao.findByProductJoin(Long.valueOf(id));
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<ingredients.size(); k++)
				{
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("id", ingredients.get(k).getId());
						obj.put("Name", ingredients.get(k).getNome());
						obj.put("Price", ingredients.get(k).getPrezzo());
						jArray.put(obj);
					}catch(Exception e) {e.printStackTrace();}
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
				
				
				
		
	}
}
