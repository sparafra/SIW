package modelHibernate;

import java.util.ArrayList;
import java.util.List;

public class Newsletter {

	String Mail;
	Long idLocale;
	
    public Newsletter(String Mail, Long idLocale)
    {
        this.Mail = Mail;
        this.idLocale= idLocale;
    }
   
    public  Newsletter()
    {
    	this.Mail = null;
    }

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}

   
    
}
