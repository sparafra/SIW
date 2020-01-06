package model;

public class Restaurant {

	Long id;
	String Name;
	String Address;
	String Mail;
	String Telephone;
	String logoURL;
	String backgroundURL;
	Boolean Active;
	
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
	
}
