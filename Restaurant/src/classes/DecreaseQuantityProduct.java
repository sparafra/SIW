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



public class DecreaseQuantityProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idProduct = Long.valueOf(req.getParameter("idProduct"));
				DBConnection dbConnection = DBConnection.getInstance(); 
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
					boolean removed = false;
					for(int k=0; k<cart.size() && !presente; k++)
					{
						if(cart.getListProducts().get(k).getId() == product.getId())
						{
							if(cart.getListProducts().get(k).getQuantita() == 1)
							{
								cart.getListProducts().remove(k);
								removed = true;
								JSONArray jArray = new JSONArray();
								
								for(int i=0; i<cart.size(); i++)
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
								resp.getWriter().write(jArray.toString());
							}
							else
								cart.getListProducts().get(k).setQuantita(cart.getListProducts().get(k).getQuantita()-1);
							presente=true;
						}
					}
					if(presente && !removed )
						resp.getWriter().write("Ok");
					else if(!removed || !presente)
						resp.getWriter().write("Error");
					else if(removed)
						resp.getWriter().write("Ok");

						
				}
				
				
				
				
				
				
				
		
	}
}
