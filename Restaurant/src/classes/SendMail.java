package classes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import modelHibernate.Email;



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
