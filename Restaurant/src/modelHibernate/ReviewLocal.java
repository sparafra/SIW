package modelHibernate;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="review_local_user")
public class ReviewLocal extends Review {
	
	@EmbeddedId
	ReviewLocalUserId id;
	
	@ManyToOne
	@MapsId("idRestaurant")
	@JoinColumn(name = "idRestaurant")
	Restaurant restaurant;
	
	
	
	//Long idLocale;
	String Recensione;
	
	public ReviewLocal(Restaurant restaurant, User user, int Voto, String Recensione, Date DataOra)
	{
		super(user, Voto, DataOra);
		this.id = new ReviewLocalUserId(restaurant.getId(), user.getNumeroTelefono());
		this.restaurant = restaurant;
		//this.user = user;
		this.Recensione = Recensione;
	}
	public ReviewLocal() {super();}
	
	/*
	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
	*/
	public String getRecensione() {
		return Recensione;
	}

	public void setRecensione(String recensione) {
		Recensione = recensione;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}
