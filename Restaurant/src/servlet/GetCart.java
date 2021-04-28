package servlet;

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
import database.UserDaoJDBC;
import model.Cart;
import model.Product;
import model.User;



public class GetCart extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				Cart cart = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				JSONArray jArray = new JSONArray();
				
				if(session != null)
				{
					cart = (Cart)session.getAttribute("Cart");
					
					for(int k=0; k<cart.size(); k++)
					{
						JSONObject obj = new JSONObject();
						try
						{
							obj.put("id", cart.getListProducts().get(k).getId());
							obj.put("Name", cart.getListProducts().get(k).getNome());
							obj.put("Price", cart.getListProducts().get(k).getPrezzo());
							obj.put("Type", cart.getListProducts().get(k).getTipo());
							obj.put("ImageURL", cart.getListProducts().get(k).getImageURL());
							obj.put("Quantity", cart.getListProducts().get(k).getQuantita());
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					}
					resp.getWriter().write(jArray.toString());
				}
				else
				{
					resp.getWriter().write(jArray.toString());
				}
				
				
				

			
		
	}
}
