package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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



public class OrdersByState extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				String Stato = null;
				try {
					Stato = req.getParameter("Stato");
				}catch(Exception e){}
				Long idLocal = Long.valueOf(req.getParameter("idLocale"));

				
				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrdersDao = new OrderDaoJDBC(dbConnection);
				
				JSONArray jArray = new JSONArray();
				
				
				List<Order> orders = OrdersDao.findAllByState(idLocal, Stato);
				
				for(int k=0; k<orders.size(); k++)
				{
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("idOrdine", orders.get(k).getId());
						obj.put("Stato", orders.get(k).getStato());
						obj.put("Asporto", orders.get(k).getAsporto());
						obj.put("NumeroTelefono", orders.get(k).getNumeroTelefono());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						obj.put("DataOra", dateFormat.format(orders.get(k).getDateTime()));
						//obj.put("DataOra", orders.get(k).getDateTime());
						obj.put("Costo", orders.get(k).getTotaleCosto());
						obj.put("Pagato", orders.get(k).getPagato());

						
						JSONArray jArrayP = new JSONArray();
						for(int i=0; i<orders.get(k).getListProducts().size(); i++)
						{
							JSONObject objP = new JSONObject();
							try
							{
								objP.put("idProdotto", orders.get(k).getListProducts().get(i).getId());
								objP.put("Name", orders.get(k).getListProducts().get(i).getNome());
								objP.put("Price", orders.get(k).getListProducts().get(i).getPrezzo());
								objP.put("Type", orders.get(k).getListProducts().get(i).getTipo());
								objP.put("ImageURL", orders.get(k).getListProducts().get(i).getImageURL());
								objP.put("Quantity", orders.get(k).getListProducts().get(i).getQuantita());
								
								JSONArray jArrayI = new JSONArray();
								for(int j=0; j<orders.get(k).getListProducts().get(i).getListIngredienti().size(); j++)
								{
									JSONObject objI = new JSONObject();
									try
									{
										objI.put("idIngredient", orders.get(k).getListProducts().get(i).getListIngredienti().get(j).getId());
										objI.put("Name", orders.get(k).getListProducts().get(i).getListIngredienti().get(j).getNome());
										objI.put("Price", orders.get(k).getListProducts().get(i).getListIngredienti().get(j).getPrezzo());
										jArrayI.put(objI);
										
									}catch(Exception e) {e.printStackTrace();}
								}
								objP.put("Ingredients", jArrayI);
								
								jArrayP.put(objP);
								
							}catch(Exception e) {e.printStackTrace();}
						}
						obj.put("Products", jArrayP);
						
						jArray.put(obj);
					}catch(Exception e) {e.printStackTrace();}
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
			
		
	}
}