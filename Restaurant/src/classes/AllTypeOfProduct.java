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


import modelHibernate.Type;
import serviceHibernate.TypeService;
import modelHibernate.Error;



public class AllTypeOfProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				try {
					TypeService type_service = new TypeService();
					
					List<Type> types = type_service.findAll();
					
					JSONArray jArray = new JSONArray();
					
					for(Type t: types)
						jArray.put(t.getJson());
					
					resp.getWriter().write(jArray.toString());					
					
		
				}
				catch(Exception e)
				{
					resp.getWriter().write(Error.GENERIC_ERROR.toString());	
				}
		
		
				
				
				
				
				
		
	}
}
