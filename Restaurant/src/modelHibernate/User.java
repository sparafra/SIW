package modelHibernate;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="user")
public class User {
	
	@Id
    String NumeroTelefono;
    String Nome;
    String Cognome;
    String Mail;
    String Indirizzo;
    String Password;
    boolean Confermato;
    boolean Amministratore;
    //Long idLocale;
    boolean Disabilitato;

    @ManyToMany(targetEntity = Restaurant.class, cascade = { CascadeType.ALL })
    @JoinTable(name = "restaurant_user", 
	joinColumns = { @JoinColumn(name = "idUser") }, 
	inverseJoinColumns = { @JoinColumn(name = "idRestaurant") })
    List<Restaurant> listRestaurants;
    
    @OneToMany(targetEntity = Order.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="NumeroTelefono")
    List<Order> listOrders;
    
    @OneToMany(targetEntity = Log.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="NumeroTelefono")
	List<Log> listLogs;
    
    @OneToMany(mappedBy = "restaurant", targetEntity = ReviewLocal.class)
    List<ReviewLocal> listReviewLocal;
    
    @OneToMany(mappedBy = "product", targetEntity = ReviewProduct.class)
    List<ReviewProduct> listReviewProduct;

    public User(){Disabilitato = false; }
    public User(String NumeroTelefono, String Nome, String Cognome, String Mail, String Indirizzo, String Password, boolean Confermato, boolean Amministratore, List<Restaurant> Locali, boolean Disabilitato)
    {
        this.NumeroTelefono = NumeroTelefono;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Mail = Mail;
        this.Indirizzo = Indirizzo;
        this.Password = Password;
        this.Confermato = Confermato;
        this.Amministratore = Amministratore;
        //this.listRestaurants = Locali;
        this.Disabilitato = Disabilitato;
    }


    public boolean equals(Object object) {
        User u = (User) object;
        return (this.NumeroTelefono == u.getNumeroTelefono() && this.Mail == u.getMail());
    }
    /*
    public Long getIdLocale() {
		return idLocale;
	}
	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
	*/
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
	public List<Order> getListOrders() {
		return listOrders;
	}
	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}
	public List<ReviewLocal> getListReviewLocal() {
		return listReviewLocal;
	}
	public void setListReviewLocal(List<ReviewLocal> listReviewLocal) {
		this.listReviewLocal = listReviewLocal;
	}
	public List<ReviewProduct> getListReviewProduct() {
		return listReviewProduct;
	}
	public void setListReviewProduct(List<ReviewProduct> listReviewProduct) {
		this.listReviewProduct = listReviewProduct;
	}
	public List<Log> getListLogs() {
		return listLogs;
	}
	public void setListLogs(List<Log> listLogs) {
		this.listLogs = listLogs;
	}
    
}
