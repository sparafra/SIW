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

public class SaveProduct extends HttpServlet implements ServletContextListener{
	
	/**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "uploads";
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("PATH: " + request.getServletContext().getResourcePaths("/assets/products/Restaurants/"));
    	String rootPath = System.getProperty("catalina.home");
    	ServletContext ctx = request.getServletContext();
    	String relativePath = ctx.getInitParameter("tempfile.dir");
    	System.out.println(rootPath+"/"+relativePath);
    	
    	
    	
    	Product product = new Product();
		DBConnection dbConnection = new DBConnection(); 
    	ProductDaoJDBC ProductDao = new ProductDaoJDBC(dbConnection);
    	IngredientDaoJDBC IngredientDao = new IngredientDaoJDBC(dbConnection);
    	
    	String Nome = null;
    	String Tipo = null;
    	Float Prezzo = null;
    	String[] IngredientsOfProduct = null;
    	
    	try {
	    	//GET DATA PRODUCT 
	    	Nome = request.getParameter("Nome");
	    	System.out.println(Nome);
	    	Tipo = request.getParameter("TipoSelect");
	    	System.out.println(Tipo);
	    	Prezzo = Float.valueOf(request.getParameter("Prezzo"));
	    	System.out.println(Prezzo);

	    	IngredientsOfProduct = request.getParameterValues("NomeIng");
	    	
	    	
	    	
	    	
    	}catch(Exception e) {}
    	
    	if(Nome == null || Tipo == null || Prezzo == null || IngredientsOfProduct == null)
    	{
    		request.setAttribute("message", "Errore! Controlla di aver compilato ogni campo e inserito tutte e 3 le immagini. ");
            getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
                    request, response);
    	}
    	else
    	{
	    	if(Nome.equals("") || Tipo.equals("null") || Prezzo == 0 || IngredientsOfProduct.length == 0 )
	    	{
	    		request.setAttribute("message", "Errore! Controlla di aver compilato ogni campo e inserito tutte e 3 le immagini. ");
	            getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
	                    request, response);
	    	}
	    	else
	    	{
	    		product.setNome(Nome);
	    		product.setPrezzo(Prezzo);
	    		product.setTipo(Tipo);
	    		
	    		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	    		for(int k=0; k<IngredientsOfProduct.length; k++)
	    		{
	    			ingredients.add(IngredientDao.findByPrimaryKeyJoin(Long.valueOf(IngredientsOfProduct[k])));
	    			System.out.println("INGREDIENT VALUE " + IngredientsOfProduct[k]);
	    		}
	    		product.setListIngredienti(ingredients);
	    		
	    	}
    	}
    	
		
		
		
    	System.out.println(Nome);
    	System.out.println(Tipo);
    	System.out.println(Prezzo);
    	
    	
    	
    	// gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        
        System.out.println(applicationPath);
        // constructs path of the directory to save uploaded file
        //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        
        HttpSession session = request.getSession(false);
        Restaurant Rest = null;
		if(session != null)
		{
			Rest = (Restaurant)session.getAttribute("Restaurant");
			product.setIdLocale(Rest.getId());
		}
		else
		{
			request.setAttribute("message", "ERRORE: Non sei loggato nel sistema");
	        getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
	                request, response);
		}
        
        
        
        
        String fileName = null;
        //Get all the parts from request and write it to the file on server
        
        //Checking Images are available
        for (Part part : request.getParts()) {
        	System.out.println(part.getName());
        	if(part.getName().equals("MainImage[]") || part.getName().equals("Thumbnail[]") || part.getName().equals("Slider[]"))
        	{
        		fileName = getFileName(part);
        		if(fileName.equals("") ||fileName.equals("null") || fileName == null)
        		{
        			request.setAttribute("message", "Errore! Controlla di aver inserito tutte e 3 le immagini. ");
                    getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
                            request, response);
        		}
        	}
        }

        String uploadFilePath = "assets/images/Restaurants/"+ Rest.getName()+ "/products/"+ Tipo +"/" + Nome +"/";
        // creates the save directory if it does not exists
        //File fileSaveDir = new File(applicationPath+uploadFilePath);
        File fileSaveDir = new File(applicationPath + uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        
        for (Part part : request.getParts()) {
        	System.out.println(part.getName());
        	if(part.getName().equals("MainImage[]"))
        	{
        		//System.out.println(part.getHeader("files[]"));
	            fileName = getFileName(part);
	            System.out.println(fileName);
	            //part.write(uploadFilePath + File.separator + fileName);
	            System.out.println(part.getSubmittedFileName());
	            
	            String fName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	            InputStream fileContent = part.getInputStream();
	            
	            byte[] buffer = new byte[fileContent.available()];
	            fileContent.read(buffer);
	         
	            File fileDir = new File(applicationPath + uploadFilePath + "MainImage/");

	            //File fileDir = new File(applicationPath+uploadFilePath + "MainImage/");
	            if (!fileDir.exists()) {
	            	fileDir.mkdirs();
	            }
	            
	            //File targetFile = new File(applicationPath+uploadFilePath + "/MainImage/" + Nome +".png");
	            File targetFile = new File(applicationPath + uploadFilePath + "/MainImage/" + Nome +".png");

	            OutputStream outStream = new FileOutputStream(targetFile);
	            outStream.write(buffer);
	            
	            //product.setImageURL(targetFile.toURI().toURL().toString());
	            //product.setImageURL(applicationPath+uploadFilePath + "MainImage/" + Nome +".png");
	            product.setImageURL(uploadFilePath + "MainImage/" + Nome +".png");

        	}
        	else if(part.getName().equals("Thumbnail[]"))
        	{
        		//System.out.println(part.getHeader("files[]"));
	            fileName = getFileName(part);
	            System.out.println(fileName);
	            //part.write(uploadFilePath + File.separator + fileName);
	            System.out.println(part.getSubmittedFileName());
	            
	            String fName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	            InputStream fileContent = part.getInputStream();
	            
	            BufferedImage image = ImageIO.read(fileContent);
	            int height = image.getHeight();
	            int width = image.getWidth();
	            
	            BufferedImage ScaledImage = resize(image, 100, 218 );

	            System.out.println(height);
	            System.out.println(width);
	            
	            byte[] buffer = new byte[fileContent.available()];
	            fileContent.read(buffer);
	            
	            File fileDir = new File(applicationPath + uploadFilePath + "Thumbnail/");
	            if (!fileDir.exists()) {
	            	fileDir.mkdirs();
	            }
	         
	            File targetFile = new File(applicationPath+uploadFilePath + "Thumbnail/"+ Nome +".png");
	            //File targetFile = new File(applicationPath + uploadFilePath + "/MainImage/" + Nome +".png");

	            //OutputStream outStream = new FileOutputStream(targetFile);
	            //outStream.write(buffer);
	            
	            ImageIO.write(ScaledImage, "png", targetFile);
	            
        	}
        	else if(part.getName().equals("Slider[]"))
        	{
        		//System.out.println(part.getHeader("files[]"));
	            fileName = getFileName(part);
	            System.out.println(fileName);
	            //part.write(uploadFilePath + File.separator + fileName);
	            System.out.println(part.getSubmittedFileName());
	            
	            String fName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	            InputStream fileContent = part.getInputStream();
	            
	            BufferedImage image = ImageIO.read(fileContent);
	            int height = image.getHeight();
	            int width = image.getWidth();
	            
	            BufferedImage ScaledImage = resize(image, 580, 1170 );

	            System.out.println(height);
	            System.out.println(width);
	            
	            byte[] buffer = new byte[fileContent.available()];
	            fileContent.read(buffer);
	         
	            File fileDir = new File(applicationPath + uploadFilePath + "Slider/");
	            if (!fileDir.exists()) {
	            	fileDir.mkdirs();
	            }
	            
	            File targetFile = new File(applicationPath+uploadFilePath + "Slider/" + Nome +".png");
	            //File targetFile = new File(applicationPath + uploadFilePath + "/MainImage/" + Nome +".png");

	            //OutputStream outStream = new FileOutputStream(targetFile);
	            //outStream.write(buffer);
	            
	            ImageIO.write(ScaledImage, "png", targetFile);

        	}
        }
        
        
        
        ProductDao.save(product);
        request.setAttribute("message", "Prodotto Inserito Correttamente! ");
        getServletContext().getRequestDispatcher("/Dashboard/default/result.jsp").forward(
                request, response);
    }
 
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }    
    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
}
