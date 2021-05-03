package classes;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelHibernate.Ingredient;
import modelHibernate.Error;
import serviceHibernate.IngredientService;




public class UpdateIngrediente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				Long idIngrediente = Long.valueOf(req.getParameter("idIngrediente"));

				String Nome = req.getParameter("Nome");
				Float Costo = Float.valueOf(req.getParameter("Costo"));
				
				//System.out.println(idIngrediente);;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				IngredientService ingredient_service = new IngredientService();
				Ingredient ing = ingredient_service.findById(idIngrediente);
				
				
				ing.setName(Nome);
				ing.setPrice(Costo);
				
				
				ingredient_service.update(ing);
			
				resp.getWriter().write(Error.COMPLETED.toString());

		
	}
}
