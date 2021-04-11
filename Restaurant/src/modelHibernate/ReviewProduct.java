package modelHibernate;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "review_product_user")
public class ReviewProduct extends Review {

	@EmbeddedId
	ReviewProductUserId id;
	
	@ManyToOne
	@MapsId("idProduct")
	@JoinColumn(name = "idProduct")
	Product product;
	
	
	public ReviewProduct(Product product, User user, int Voto, Date DataOra)
	{
		super(user, Voto, DataOra);
		this.id = new ReviewProductUserId(product.getId(), user.getNumeroTelefono());
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
	
}
