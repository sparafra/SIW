package model;

public class User {



    String NumeroTelefono;
    String Nome;
    String Cognome;
    String Mail;
    String Indirizzo;
    String Password;
    boolean Confermato;
    boolean Amministratore;
    Long idLocale;
    boolean Disabilitato;

    public User(){Disabilitato = false; }
    public User(String NumeroTelefono, String Nome, String Cognome, String Mail, String Indirizzo, String Password, boolean Confermato, boolean Amministratore, Long idLocale, boolean Disabilitato)
    {
        this.NumeroTelefono = NumeroTelefono;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Mail = Mail;
        this.Indirizzo = Indirizzo;
        this.Password = Password;
        this.Confermato = Confermato;
        this.Amministratore = Amministratore;
        this.idLocale = idLocale;
        this.Disabilitato = Disabilitato;
    }


    public boolean equals(Object object) {
        User u = (User) object;
        return (this.NumeroTelefono == u.getNumeroTelefono() && this.idLocale == u.getIdLocale() && this.Mail == u.getMail());
    }
    
    public Long getIdLocale() {
		return idLocale;
	}
	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
	public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public boolean getConfermato() {
        return Confermato;
    }

    public void setConfermato(boolean confermato) {
        Confermato = confermato;
    }
    public boolean getAmministratore() {
        return Amministratore;
    }

    public void setAmministratore(boolean amministratore) {
        Amministratore = amministratore;
    }
	public boolean getDisabilitato() {
		return Disabilitato;
	}
	public void setDisabilitato(boolean disabilitato) {
		Disabilitato = disabilitato;
	}
    
}
