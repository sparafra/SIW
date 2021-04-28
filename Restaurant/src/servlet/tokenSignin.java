package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;
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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import database.DBConnection;
import database.UserDaoJDBC;
import model.Cart;
import model.User;



public class tokenSignin extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Token = req.getParameter("Token");
				System.out.println(Token);
				
				HttpTransport httpTransport=null;
				try {
					httpTransport = GoogleNetHttpTransport.newTrustedTransport();
					System.out.println("ok");
				} catch (GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
				GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
					    // Specify the CLIENT_ID of the app that accesses the backend:
					    .setAudience(Collections.singletonList("349467334789-ukhc1pr792dsqam714l6qrb4krcpon5t.apps.googleusercontent.com"))
					    // Or, if multiple clients access the backend:
					    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
					    .build();

					// (Receive idTokenString by HTTPS POST)

					GoogleIdToken idToken = null;
					try {
						idToken = verifier.verify(Token);
						System.out.println("ok");
					} catch (GeneralSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (idToken != null) {
					  Payload payload = idToken.getPayload();

					  // Print user identifier
					  String userId = payload.getSubject();
					  System.out.println("User ID: " + userId);
					  
					  // Get profile information from payload
					  String email = payload.getEmail();
					  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
					  String name = (String) payload.get("name");
					  String pictureUrl = (String) payload.get("picture");
					  String locale = (String) payload.get("locale");
					  String familyName = (String) payload.get("family_name");
					  String givenName = (String) payload.get("given_name");
					  
					  System.out.println("Name: " + givenName);
					  System.out.println("Cognome: " + familyName);
					  System.out.println("Locale: " + locale);
					  
					  // Use or store profile information
					  // ...
					  
					  DBConnection dbConnection = new DBConnection(); 
					  UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
					  User user = UserDao.findByMailJoin(email);
						
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						if(user == null)
						{
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Utente Non Trovato");
							resp.getWriter().write(obj.toString());
						}
						else if(user.getMail().equals(email))
						{
							HttpSession session = req.getSession(true);
							session.setAttribute("UserLogged", user);
							session.setAttribute("Cart", new Cart());
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Logged");
							System.out.println("login");
							resp.getWriter().write(obj.toString());
						}
						else
						{
							JSONObject obj = new JSONObject();
							obj.put("Stato", "Password errata");
							resp.getWriter().write(obj.toString());	
						}

					} else {
					  System.out.println("Invalid ID token.");
					  resp.getWriter().write("Invalid id token");	
					}
				
				/*
				DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/ristorante?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "prova", "prova"); 
				UserDaoJDBC UserDao = new UserDaoJDBC(dbConnection);
				User user = UserDao.findByMailJoin(Mail);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user == null)
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Utente Non Trovato");
					resp.getWriter().write(obj.toString());
				}
				else if(user.getPassword().equals(Password))
				{
					HttpSession session = req.getSession(true);
					session.setAttribute("UserLogged", user);
					session.setAttribute("Cart", new Cart());
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Logged");

					resp.getWriter().write(obj.toString());
				}
				else
				{
					JSONObject obj = new JSONObject();
					obj.put("Stato", "Password errata");
					resp.getWriter().write(obj.toString());	
				}
				*/
			
		
	}
}
