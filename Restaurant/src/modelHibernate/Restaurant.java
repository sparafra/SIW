package modelHibernate;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="restaurant")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;
	
	String Name;
	String Address;
	String Mail;
	String Telephone;
	String logoURL;
	String backgroundURL;
	Boolean Active;
	
	@OneToMany(targetEntity = Product.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="idRestaurant")
	List<Product> listProducts;
	
	@OneToMany(targetEntity = Log.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="idRestaurant")
	List<Log> listLogs;
	
	@OneToMany(targetEntity = Notice.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="idRestaurant")
	List<Notice> listNotices;
	
	@OneToMany(targetEntity = Order.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="idRestaurant")
	List<Order> listOrders;
	
	@OneToMany(targetEntity = Analytic.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="idRestaurant")
	List<Analytic> listAnalytics;
	
	@ManyToMany(targetEntity = User.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "restaurant_user", 
				joinColumns = { @JoinColumn(name = "idRestaurant") }, 
				inverseJoinColumns = { @JoinColumn(name = "idUser") })
	List<User> listUsers;
	
    @OneToMany(mappedBy = "user")
    List<ReviewLocal> listReviewLocal;

	
	public Restaurant(Long Id, String Name, String Address, String Mail, String Telephone, String logoURL, String backgroundURL, Boolean Active)
	{
		this.id = Id;
		this.Name = Name;
		this.Address = Address;
		this.Mail = Mail;
		this.Telephone = Telephone;
		this.logoURL = logoURL;
		this.backgroundURL = backgroundURL;
		this.Active = Active;
	}
	public Restaurant(String Name, String Address, String Mail, String Telephone, String logoURL, String backgroundURL, Boolean Active)
	{
		this.Name = Name;
		this.Address = Address;
		this.Mail = Mail;
		this.Telephone = Telephone;
		this.logoURL = logoURL;
		this.backgroundURL = backgroundURL;
		this.Active = Active;
	}
	public Restaurant() {}

	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	public Boolean getActive() {
		return Active;
	}
	public void setActive(Boolean active) {
		Active = active;
	}
	public String getBackgroundURL() {
		return backgroundURL;
	}
	public void setBackgroundURL(String backgroundURL) {
		this.backgroundURL = backgroundURL;
	}
	public List<User> getListUsers() {
		return listUsers;
	}
	public void setListUsers(List<User> listUsers) {
		this.listUsers = listUsers;
	}
	public List<Product> getListProducts() {
		return listProducts;
	}
	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
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
	public List<Log> getListLogs() {
		return listLogs;
	}
	public void setListLogs(List<Log> listLogs) {
		this.listLogs = listLogs;
	}
	
	
}
