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

import modelHibernate.Product;
import modelHibernate.ProductOrder;
import serviceHibernate.ProductService;
import modelHibernate.Error;
import modelHibernate.Order;



public class DecreaseQuantityProduct extends HttpServlet{
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
					
					boolean presente=false;
					boolean removed = false;
					for(int k=0; k<cart.getListProductOrder().size() && !presente; k++)
					{
						if(cart.getListProductOrder().get(k).getProduct().getId() == product.getId())
						{
							if(cart.getListProductOrder().get(k).getQuantity() == 1)
							{
								cart.getListProductOrder().remove(k);
								removed = true;
								JSONArray jArray = new JSONArray();
								
								for(ProductOrder po: cart.getListProductOrder())
									jArray.put(po.getJson());
								resp.getWriter().write(jArray.toString());

								/*
								for(int i=0; i<cart.getListProductOrder().size(); i++)
								{
									JSONObject obj = new JSONObject();
									try
									{
										obj.put("id", cart.getListProducts().get(i).getId());
										obj.put("Name", cart.getListProducts().get(i).getNome());
										obj.put("Price", cart.getListProducts().get(i).getPrezzo());
										obj.put("Type", cart.getListProducts().get(i).getTipo());
										obj.put("ImageURL", cart.getListProducts().get(i).getImageURL());
										obj.put("Quantity", cart.getListProducts().get(i).getQuantita());
										jArray.put(obj);
									}catch(Exception e) {e.printStackTrace();}
								}
								*/
							}
							else
								cart.getListProductOrder().get(k).setQuantity(cart.getListProductOrder().get(k).getQuantity()-1);
							presente=true;
						}
					}
					if(presente && !removed )
						resp.getWriter().write(Error.COMPLETED.toString());
					else if(!removed || !presente)
						resp.getWriter().write(Error.GENERIC_ERROR.toString());
					else if(removed)
						resp.getWriter().write(Error.COMPLETED.toString());

						
				}
				
				
				
				
				
				
				
		
	}
}
