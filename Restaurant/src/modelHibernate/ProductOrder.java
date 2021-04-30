package modelHibernate;

import java.util.List;
import javax.persistence.*;

import org.json.JSONArray;
import org.json.JSONObject;  

@Entity
@Table(name="product_order")
public class ProductOrder {
	
	@EmbeddedId
	ProductOrderId id;
	
	
	@ManyToOne
	@MapsId("product_id")
	@JoinColumn(name = "product_id")
	Product product;
	
	@ManyToOne
	@MapsId("order_id")
	@JoinColumn(name = "order_id")
	Order order;
	
	
	int quantity;
	
	public ProductOrder() {}
	
    public ProductOrder(Product product, Order order, int quantity)
    {
    	this.id = new ProductOrderId(product.getId(), order.getId());
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
 
	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id.getJson());
		obj.put("product", product.getJson());
		obj.put("order", order.getJson());
		obj.put("quantity", quantity);
		
		
		return obj;
	}

}
