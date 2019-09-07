package app;

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
import database.OrderDaoJDBC;
import model.Order;
import model.Product;
import model.Restaurant;
import model.User;



public class OrdersById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idOrder = Long.valueOf(req.getParameter("idOrdine"));

				
				
				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrdersDao = new OrderDaoJDBC(dbConnection);
				
				JSONObject obj = new JSONObject();

				
				Order order = OrdersDao.findByPrimaryKeyJoin(idOrder);
			
				
				try
				{
					obj.put("idOrdine", order.getId());
					obj.put("Stato", order.getStato());
					obj.put("Asporto", order.getAsporto());
					obj.put("NumeroTelefono", order.getNumeroTelefono());
					obj.put("DataOra", order.getDateTime());
					obj.put("Costo", order.getTotaleCosto());
					obj.put("Pagato", order.getPagato());
					
					JSONArray jArrayP = new JSONArray();
					for(int i=0; i<order.getListProducts().size(); i++)
					{
						JSONObject objP = new JSONObject();
						try
						{
							objP.put("idProdotto",order.getListProducts().get(i).getId());
							objP.put("Name", order.getListProducts().get(i).getNome());
							objP.put("Price", order.getListProducts().get(i).getPrezzo());
							objP.put("Type", order.getListProducts().get(i).getTipo());
							objP.put("ImageURL", order.getListProducts().get(i).getImageURL());
							objP.put("Quantity", order.getListProducts().get(i).getQuantita());
							
							JSONArray jArrayI = new JSONArray();
							for(int j=0; j<order.getListProducts().get(i).getListIngredienti().size(); j++)
							{
								JSONObject objI = new JSONObject();
								try
								{
									objI.put("idIngredient", order.getListProducts().get(i).getListIngredienti().get(j).getId());
									objI.put("Name", order.getListProducts().get(i).getListIngredienti().get(j).getNome());
									objI.put("Price", order.getListProducts().get(i).getListIngredienti().get(j).getPrezzo());
									jArrayI.put(objI);
									
								}catch(Exception e) {e.printStackTrace();}
							}
							objP.put("Ingredients", jArrayI);
							
							jArrayP.put(objP);
							
						}catch(Exception e) {e.printStackTrace();}
					}
					obj.put("Products", jArrayP);
					
				}catch(Exception e) {e.printStackTrace();}
						
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());
				
			
		
	}
}
