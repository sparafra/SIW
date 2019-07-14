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

import database.DBConnection;
import database.ProductDaoJDBC;
import model.Cart;
import model.Product;



public class IncreaseQuantityProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idProduct = Long.valueOf(req.getParameter("idProduct"));
				DBConnection dbConnection = new DBConnection(); 
				ProductDaoJDBC ProdDao = new ProductDaoJDBC(dbConnection);
				Product product = ProdDao.findByPrimaryKeyJoin(idProduct);
				Cart cart = null;

				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					cart = (Cart)session.getAttribute("Cart");
					
					boolean presente=false;
					for(int k=0; k<cart.size() && !presente; k++)
					{
						if(cart.getListProducts().get(k).getId() == product.getId())
						{
							cart.getListProducts().get(k).setQuantita(cart.getListProducts().get(k).getQuantita()+1);
							presente=true;
						}
					}
					if(presente)
						resp.getWriter().write("Ok");
					else
						resp.getWriter().write("Error");
				}
				
				
				
				
				
				
				
		
	}
}
