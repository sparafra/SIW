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
import database.OrderDaoJDBC;
import model.Order;
import model.Product;
import model.Restaurant;
import model.User;



public class OrdersByUser extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				String Stato = null;
				try {
					Stato = req.getParameter("Stato");
				}catch(Exception e){}
				
				User user = null;
				Restaurant Rest = null;
				DBConnection dbConnection = new DBConnection(); 
				OrderDaoJDBC OrdersDao = new OrderDaoJDBC(dbConnection);
				
				JSONArray jArray = new JSONArray();
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					user = (User)session.getAttribute("UserLogged");
					Rest = (Restaurant)session.getAttribute("Restaurant");
					if(Rest != null)
					{
						List<Order> orders;
						if(Stato==null)
							orders = OrdersDao.findByUserLocalJoin(user.getNumeroTelefono(), Rest.getId());
						else
							orders = OrdersDao.findByUserLocalStateJoin(user.getNumeroTelefono(), Rest.getId(), Stato);

						for(int k=0; k<orders.size(); k++)
						{
							JSONObject obj = new JSONObject();
							try
							{
								obj.put("idOrdine", orders.get(k).getId());
								obj.put("Stato", orders.get(k).getStato());
								obj.put("Asporto", orders.get(k).getAsporto());
								obj.put("NumeroTelefono", orders.get(k).getNumeroTelefono());
								obj.put("DataOra", orders.get(k).getDateTime());
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
					}
				}
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
				
			
		
	}
}
