package modelHibernate;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;  

@Embeddable
public class ProductOrderId implements Serializable{
	
	@Column(name = "product_id")
	Long product_id;
	
	@Column(name = "order_id")
	Long order_id;
	
	public ProductOrderId()
	{
		
	}
	public ProductOrderId(Long product_id, Long order_id)
	{
		super();
		this.product_id = product_id;
		this.order_id = order_id;
	}

	public Long getIdProduct() {
		return product_id;
	}

	public void setIdProduct(Long product_id) {
		this.product_id = product_id;
	}

	public Long getIdOrder() {
		return order_id;
	}

	public void setIdOrder(Long order_id) {
		this.order_id = order_id;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((product_id == null) ? 0 : product_id.hashCode());
        result = prime * result
                + ((order_id == null) ? 0 : order_id.hashCode());
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
        ProductOrderId other = (ProductOrderId) obj;
        return Objects.equals(getIdProduct(), other.getIdProduct()) && Objects.equals(getIdOrder(), other.getIdOrder());
    }
}
