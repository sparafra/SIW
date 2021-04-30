package modelHibernate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;


@Entity
@Table(name="restaurant")
public class Restaurant {
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "restaurant_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )	
	Long id;
	
	String name;
	String address;
	String mail;
	String telephone;
	String logo_url;
	String background_url;
	Boolean active;
	
	@OneToMany(targetEntity = Product.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
	List<Product> listProducts;
	
	@OneToMany(targetEntity = Log.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
	List<Log> listLogs;
	
	@OneToMany(targetEntity = Notice.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
	List<Notice> listNotices;
	
	@OneToMany(targetEntity = Order.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
	List<Order> listOrders;
	
	@OneToMany(targetEntity = Analytic.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
	List<Analytic> listAnalytics;
	
	@ManyToMany(targetEntity = User.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "restaurant_user", 
				joinColumns = { @JoinColumn(name = "restaurant_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") })
	List<User> listUsers;
	
    @OneToMany(mappedBy = "user")
    List<ReviewRestaurant> listReviewRestaurant;


    
	public Restaurant(String name, String address, String mail, String telephone, String logo_url,
			String background_url, Boolean active, List<Product> listProducts, List<Log> listLogs,
			List<Notice> listNotices, List<Order> listOrders, List<Analytic> listAnalytics, List<User> listUsers,
			List<ReviewRestaurant> listReviewRestaurant) {
		super();
		this.name = name;
		this.address = address;
		this.mail = mail;
		this.telephone = telephone;
		this.logo_url = logo_url;
		this.background_url = background_url;
		this.active = active;
		this.listProducts = listProducts;
		this.listLogs = listLogs;
		this.listNotices = listNotices;
		this.listOrders = listOrders;
		this.listAnalytics = listAnalytics;
		this.listUsers = listUsers;
		this.listReviewRestaurant = listReviewRestaurant;
	}

	public Restaurant() {}

	
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getBackground_url() {
		return background_url;
	}

	public void setBackground_url(String background_url) {
		this.background_url = background_url;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public List<Log> getListLogs() {
		return listLogs;
	}

	public void setListLogs(List<Log> listLogs) {
		this.listLogs = listLogs;
	}

	public List<Notice> getListNotices() {
		return listNotices;
	}

	public void setListNotices(List<Notice> listNotices) {
		this.listNotices = listNotices;
	}

	public List<Order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}

	public List<Analytic> getListAnalytics() {
		return listAnalytics;
	}

	public void setListAnalytics(List<Analytic> listAnalytics) {
		this.listAnalytics = listAnalytics;
	}

	public List<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<User> listUsers) {
		this.listUsers = listUsers;
	}

	public List<ReviewRestaurant> getListReviewRestaurant() {
		return listReviewRestaurant;
	}

	public void setListReviewRestaurant(List<ReviewRestaurant> listReviewRestaurant) {
		this.listReviewRestaurant = listReviewRestaurant;
	}

	public JSONObject getJson()
	{
		
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("name", name);
		obj.put("address", address);
		obj.put("mail", telephone);
		obj.put("logo_url", logo_url);
		obj.put("background_url", background_url);
		obj.put("active", active);

		JSONArray products = new JSONArray();
		for(Product p: listProducts)
		{
			products.put(p.getJson());
		}
		obj.put("listProducts", products);
		
		JSONArray logs = new JSONArray();
		for(Log l: listLogs)
		{
			logs.put(l.getJson());
		}
		obj.put("listLogs", logs);
		
		JSONArray notices = new JSONArray();
		for(Notice n: listNotices)
		{
			notices.put(n.getJson());
		}
		obj.put("listNotices", notices);
		
		JSONArray orders = new JSONArray();
		for(Order o: listOrders)
		{
			orders.put(o.getJson());
		}
		obj.put("listOrders", orders);
		
		JSONArray analytics = new JSONArray();
		for(Analytic a: listAnalytics)
		{
			analytics.put(a.getJson());
		}
		obj.put("listAnalytics", analytics);
		
		JSONArray users = new JSONArray();
		for(User u: listUsers)
		{
			users.put(u.getJson());
		}
		obj.put("listUsers", users);
		
		JSONArray reviewrestaurants = new JSONArray();
		for(ReviewRestaurant rr: listReviewRestaurant)
		{
			reviewrestaurants.put(rr.getJson());
		}
		obj.put("listReviewRestaurant", reviewrestaurants);
		
		return obj;
	}
}
