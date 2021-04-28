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
import model.Product;
import model.Restaurant;



public class ProductsByType extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Type = req.getParameter("Type");
				Restaurant Rest = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					Rest = (Restaurant)session.getAttribute("Restaurant");
					if(Rest != null)
					{
						DBConnection dbConnection = new DBConnection(); 
						ProductDaoJDBC ProdDao = new ProductDaoJDBC(dbConnection);
						List<Product> products = ProdDao.findByTypeLocalJoin(Type, Rest.getId());
						
						JSONArray jArray = new JSONArray();
						
						for(int k=0; k<products.size(); k++)
						{
							JSONObject obj = new JSONObject();
							try
							{
								obj.put("id", products.get(k).getId());
								obj.put("Name", products.get(k).getNome());
								obj.put("Price", products.get(k).getPrezzo());
								obj.put("Type", products.get(k).getTipo());
								obj.put("ImageURL", products.get(k).getImageURL());
								obj.put("Quantity", products.get(k).getQuantita());
								
								JSONArray jArrayI = new JSONArray();
								for(int i=0; i<products.get(k).getListIngredienti().size(); i++)
								{
									JSONObject objI = new JSONObject();
									try
									{
										objI.put("id", products.get(k).getListIngredienti().get(i).getId());
										objI.put("Name", products.get(k).getListIngredienti().get(i).getNome());
										objI.put("Price", products.get(k).getListIngredienti().get(i).getPrezzo());
										jArrayI.put(objI);
										
									}catch(Exception e) {e.printStackTrace();}
								}
								obj.put("Ingredients", jArrayI);
								
								JSONArray jArrayR = new JSONArray();
								for(int i=0; i<products.get(k).getListReview().size(); i++)
								{
									JSONObject objR = new JSONObject();
									try
									{
										objR.put("idProdotto", products.get(k).getListReview().get(i).getIdProduct());
										objR.put("NumeroTelefono", products.get(k).getListReview().get(i).getNumeroTelefono());
										objR.put("Voto", products.get(k).getListReview().get(i).getVoto());
										objR.put("DataOra", products.get(k).getListReview().get(i).getDataOra());
										jArrayR.put(objR);
										
									}catch(Exception e) {e.printStackTrace();}
								}
								obj.put("Reviews", jArrayR);
								jArray.put(obj);
							}catch(Exception e) {e.printStackTrace();}
						}
						
						
						resp.getWriter().write(jArray.toString());
					}
					else
					{
						resp.getWriter().write("error");
					}
					
				}
				else
				{
					resp.getWriter().write("error");
				}
				
				
				
				
				
				
		
	}
}
