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



import modelHibernate.Ingredient;
import modelHibernate.Restaurant;
import modelHibernate.User;
import modelHibernate.Error;
import serviceHibernate.IngredientService;



public class IngredientById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idIngredient = Long.valueOf(req.getParameter("idIngrediente"));

				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");


				HttpSession session = req.getSession(false);
				if(session != null)
				{
					User user = (User)session.getAttribute("UserLogged");
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
					if(Rest != null && user != null && user.isAdmin())
					{
						IngredientService ingredient_service = new IngredientService();
						Ingredient ingredient = ingredient_service.findById(idIngredient);
					
						resp.getWriter().write(ingredient.getJson().toString());
						
					}
					else
						resp.getWriter().write(Error.GENERIC_ERROR.toString());

				}
				resp.getWriter().write(Error.BLANK_SESSION.toString());

			
		
	}
}
