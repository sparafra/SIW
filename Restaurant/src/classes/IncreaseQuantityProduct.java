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

import modelHibernate.Cart;
import modelHibernate.Product;
import serviceHibernate.ProductService;
import modelHibernate.Error;



public class IncreaseQuantityProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idProduct = Long.valueOf(req.getParameter("idProduct"));
				
				ProductService product_service = new ProductService();
				
				Product product = product_service.findById(idProduct);
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
