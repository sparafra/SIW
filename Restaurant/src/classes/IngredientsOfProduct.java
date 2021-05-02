package classes;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import modelHibernate.Ingredient;
import modelHibernate.Product;
import serviceHibernate.ProductService;


public class IngredientsOfProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long id = Long.valueOf(req.getParameter("id"));
		
				ProductService product_service = new ProductService();
				Product product = product_service.findById(id);
				
				List<Ingredient> ingredients = product.getListIngredients();
				
				JSONArray jArray = new JSONArray();
				
				for(Ingredient i: ingredients)
					jArray.put(i.getJson());
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
		
		
	}
}
