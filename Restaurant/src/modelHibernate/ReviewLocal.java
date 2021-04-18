package modelHibernate;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="review_local_user")
public class ReviewLocal extends Review {
	
	@EmbeddedId
	ReviewLocalUserId id;
	
	@ManyToOne
	@MapsId("restaurant_id")
	@JoinColumn(name = "restaurant_id")
	Restaurant restaurant;
	
	
	
	String review;
	
	public ReviewLocal(Restaurant restaurant, User user, int vote, String review, Date date_time)
	{
		super(user, vote, date_time);
		this.id = new ReviewLocalUserId(restaurant.getId(), user.getTelephone());
		this.restaurant = restaurant;
		//this.user = user;
		this.review = review;
	}
	public ReviewLocal() {super();}
	
	//Getters and Setters
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	
	
}
