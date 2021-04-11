package modelHibernate;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	String host;  
	String user;  
	String password;
	String to;

    
    public Email()
    {
    	host="ssl0.ovh.net"; 
    	user="alpachino@gametechmodding.com";
    	password="alpachino";
    	to="alpachino@gametechmodding.com"; 
    	
    }
    public boolean Send(String Subject, String MessageToSend)
    {
    	//Get the session object  
    	Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.auth", "true");  
		     
		Session session = Session.getDefaultInstance(props,  
		new javax.mail.Authenticator() {  
		protected PasswordAuthentication getPasswordAuthentication() {  
			return new PasswordAuthentication(user,password);  
		  	}  
		});  
		  
		//Compose the message  
		try {  
			MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(user));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject(Subject);  
		    message.setText(MessageToSend);  
		       
		    //send the message  
		    Transport.send(message);  
		  
		    System.out.println("message sent successfully...");  
		    return true;
		   
		    } catch (MessagingException e) {e.printStackTrace(); return false;}       
    }
    public boolean Send(String SendTo, String Subject, String MessageToSend)
    {
    	to = SendTo;
    	//Get the session object  
    	Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.auth", "true");  
		     
		Session session = Session.getDefaultInstance(props,  
		new javax.mail.Authenticator() {  
		protected PasswordAuthentication getPasswordAuthentication() {  
			return new PasswordAuthentication(user,password);  
		  	}  
		});  
		  
		//Compose the message  
		try {  
			MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(user));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject(Subject);  
		    message.setText(MessageToSend);  
		       
		    //send the message  
		    Transport.send(message);  
		  
		    System.out.println("message sent successfully...");  
		    return true;
		   
		    } catch (MessagingException e) {e.printStackTrace(); return false;}       
    }
}