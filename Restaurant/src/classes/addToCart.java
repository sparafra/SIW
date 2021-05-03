package classes;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;


import modelHibernate.Order;
import modelHibernate.Product;
import modelHibernate.ProductOrder;
import modelHibernate.User;
import modelHibernate.Error;
import serviceHibernate.ProductService;



public class addToCart extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idProduct = Long.valueOf(req.getParameter("idProduct"));
				
				ProductService product_service = new ProductService();
				Product product = product_service.findById(idProduct);
		
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Order cart = (Order)session.getAttribute("Cart");
					List<ProductOrder> list = cart.getListProductOrder();	
					
					boolean presente=false;
					for(int k=0; k<list.size() && !presente; k++)
					{
						if(list.get(k).getProduct().getId() == product.getId())
						{
							list.get(k).setQuantity(list.get(k).getQuantity() + 1);
							presente=true;
						}
					}
					if(!presente)
					{
						ProductOrder po = new ProductOrder(product, cart, 1);
						cart.getListProductOrder().add(po);
						
					}
					JSONArray jArray = new JSONArray();
					
					for(ProductOrder po: cart.getListProductOrder())
					{
						jArray.put(po.getProduct());
					}
					
					resp.getWriter().write(jArray.toString());
				}
				else
				{					
					resp.getWriter().write(Error.BLANK_SESSION.toString());
				}	
	}
}
