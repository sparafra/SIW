package modelHibernate;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;  

@Embeddable
public class ReviewRestaurantUserId implements Serializable {
	
	@Column(name = "restaurant_id")
	Long restaurant_id;
	
	@Column(name = "telephone")
	String telephone;
	
	public ReviewRestaurantUserId() {}
	public ReviewRestaurantUserId(Long restaurant_id, String telephone)
	{
		super();
		this.restaurant_id = restaurant_id;
		this.telephone = telephone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((restaurant_id == null) ? 0 : restaurant_id.hashCode());
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
		ReviewRestaurantUserId other = (ReviewRestaurantUserId) obj;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (restaurant_id == null) {
			if (other.restaurant_id != null)
				return false;
		} else if (!restaurant_id.equals(other.restaurant_id))
			return false;
		return true;
	}
	public Long getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(Long restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("restaurant_id", restaurant_id);
		obj.put("telephone", telephone);
		
		return obj;
	}
}
