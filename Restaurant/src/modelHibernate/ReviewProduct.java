package modelHibernate;

import java.util.Date;
import javax.persistence.*;

import org.json.JSONObject;

@Entity
@Table(name = "review_product_user")
public class ReviewProduct extends Review {

	@EmbeddedId
	ReviewProductUserId id;
	
	@ManyToOne
	@MapsId("product_id")
	@JoinColumn(name = "product_id")
	Product product;
	
	
	public ReviewProduct(Product product, User user, int vote, Date date_time)
	{
		super(user, vote, date_time);
		this.id = new ReviewProductUserId(product.getId(), user.getTelephone());
		this.product = product;
	}
	public ReviewProduct()
	{
		super();
	}
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id.getJson());
		obj.put("product", product.getJson());
		obj.put("user", user.getJson());
		obj.put("vote", vote);
		obj.put("date_time", date_time);
		
		return obj;
	}
}
