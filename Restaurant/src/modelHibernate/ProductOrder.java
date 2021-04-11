package modelHibernate;

import java.util.List;
import javax.persistence.*;  

@Entity
@Table(name="product_order")
public class ProductOrder {
	
	@EmbeddedId
	ProductOrderId id;
	
	
	@ManyToOne
	@MapsId("idProduct")
	@JoinColumn(name = "idProduct")
	Product product;
	
	@ManyToOne
	@MapsId("idOrder")
	@JoinColumn(name = "idOrder")
	Order order;
	
	
	int Quantity;
	
	public ProductOrder() {}
	
    public ProductOrder(Product product, Order order, int quantity)
    {
    	this.id = new ProductOrderId(product.getId(), order.getId());
        this.product = product;
        this.order = order;
        this.Quantity = quantity;
    }

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
 

}
