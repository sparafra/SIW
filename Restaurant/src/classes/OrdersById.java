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
import database.OrderDaoJDBC;
import modelHibernate.Order;
import model.Product;
import modelHibernate.Restaurant;
import modelHibernate.User;
import serviceHibernate.OrderService;
import serviceHibernate.RestaurantService;
import serviceHibernate.UserService;
import modelHibernate.Error;


public class OrdersById extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Long idOrder = Long.valueOf(req.getParameter("idOrdine"));
				
				
				OrderService order_service = new OrderService();
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				

				HttpSession session = req.getSession(false);
				if(session != null)
				{
					User user = (User)session.getAttribute("UserLogged");
					Restaurant Rest = (Restaurant)session.getAttribute("Restaurant");
					
										
					if(Rest != null && user != null && user.isAdmin())
					{
						Order order = order_service.findById(idOrder);
						resp.getWriter().write(order.getJson().toString());
						//Order order = OrdersDao.findByPrimaryKeyJoin(idOrder);
						
						/*
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
						*/
					}
					
				}
				
				resp.getWriter().write(Error.BLANK_SESSION.toString());
				
			
		
	}
}
