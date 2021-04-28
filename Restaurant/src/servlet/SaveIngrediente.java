package servlet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

import database.DBConnection;
import database.IngredientDaoJDBC;
import database.ProductDaoJDBC;
import database.RestaurantDaoJDBC;
import database.UserDaoJDBC;
import model.Cart;
import model.Ingredient;
import model.Product;
import model.Restaurant;
import model.User;


@MultipartConfig

public class SaveIngrediente extends HttpServlet implements ServletContextListener{
	
	/**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "uploads";
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
 
    	
    	
    	Ingredient ingredient = new Ingredient();
		DBConnection dbConnection = new DBConnection(); 
    	IngredientDaoJDBC IngredientDao = new IngredientDaoJDBC(dbConnection);
    	
    	String Nome = null;
    	Float Prezzo = null;
    	
    	try {
	    	//GET DATA PRODUCT 
	    	Nome = request.getParameter("Nome");
	    	System.out.println(Nome);
	    	Prezzo = Float.valueOf(request.getParameter("Prezzo"));
	    	System.out.println(Prezzo);

	    	
    	}catch(Exception e) {}
    	
    	if(Nome.equals("") || Nome == null || Prezzo == 0 || Prezzo == null )
    	{
    		request.setAttribute("message", "Errore! Controlla di aver compilato ogni campo ");
            getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
                    request, response);
    	}
    	else
    	{
    		ingredient.setNome(Nome);
    		ingredient.setPrezzo(Prezzo);
    		
    		
    	}
    	
		
		
		
    	System.out.println(Nome);
    	System.out.println(Prezzo);
    	
  
    	IngredientDao.save(ingredient);
        request.setAttribute("message", "Ingrediente Inserito Correttamente! ");
        getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
                request, response);
    }
 
 
	
}
