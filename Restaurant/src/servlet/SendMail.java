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
import database.UserDaoJDBC;
import model.Cart;
import model.Email;
import model.User;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  


public class SendMail extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
		String Nome = req.getParameter("Name");
		String Oggetto = req.getParameter("Subject");
		String Mail = req.getParameter("Mail");
		String Messaggio = req.getParameter("Message");
		String SendTo = req.getParameter("To");
		
		
		String FinalMessage = "Nome: " + Nome +"\r\n" + "Oggetto: " + Oggetto +"\r\n" + "Mail: " + Mail +"\r\n" + "Messaggio: " + Messaggio;
		
		Email mail = new Email();
		mail.Send(Oggetto, FinalMessage);
		   
		
	}
}
