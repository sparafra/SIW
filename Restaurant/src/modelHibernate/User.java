package modelHibernate;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="user")
public class User {
	
	@Id
    String telephone;
    @Id
	String mail;

    String name;
    String surname;
    String address;
    String password;
    boolean approved;
    boolean admin;
    //Long idLocale;
    boolean disabled;

    @ManyToMany(targetEntity = Restaurant.class, cascade = { CascadeType.ALL })
    @JoinTable(name = "restaurant_user", 
	joinColumns = { @JoinColumn(name = "user_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "restaurant_id") })
    List<Restaurant> listRestaurants;
    
    @OneToMany(targetEntity = Order.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="telephone")
    List<Order> listOrders;
    
    @OneToMany(targetEntity = Log.class, cascade = {CascadeType.ALL})
    @JoinColumn(name="telephone")
	List<Log> listLogs;
    
    @OneToMany(mappedBy = "restaurant", targetEntity = ReviewLocal.class)
    List<ReviewLocal> listReviewLocal;
    
    @OneToMany(mappedBy = "product", targetEntity = ReviewProduct.class)
    List<ReviewProduct> listReviewProduct;

    public User(){disabled = false; }
    

    public User(String telephone, String name, String surname, String mail, String address, String password,
			boolean approved, boolean admin, boolean disabled, List<Restaurant> listRestaurants, List<Order> listOrders,
			List<Log> listLogs, List<ReviewLocal> listReviewLocal, List<ReviewProduct> listReviewProduct) {
		super();
		this.telephone = telephone;
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.address = address;
		this.password = password;
		this.approved = approved;
		this.admin = admin;
		this.disabled = disabled;
		this.listRestaurants = listRestaurants;
		this.listOrders = listOrders;
		this.listLogs = listLogs;
		this.listReviewLocal = listReviewLocal;
		this.listReviewProduct = listReviewProduct;
	}

    //Getters and Setters
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<Restaurant> getListRestaurants() {
		return listRestaurants;
	}

	public void setListRestaurants(List<Restaurant> listRestaurants) {
		this.listRestaurants = listRestaurants;
	}

	public List<Order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}

	public List<Log> getListLogs() {
		return listLogs;
	}

	public void setListLogs(List<Log> listLogs) {
		this.listLogs = listLogs;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}
	
	
    
	
}