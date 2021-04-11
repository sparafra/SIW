package modelHibernate;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;  

@Embeddable
public class ProductOrderId implements Serializable{
	
	@Column(name = "idProduct")
	Long idProduct;
	
	@Column(name = "idOrder")
	Long idOrder;
	
	public ProductOrderId()
	{
		
	}
	public ProductOrderId(Long idProduct, Long idOrder)
	{
		super();
		this.idProduct = idProduct;
		this.idOrder = idOrder;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idProduct == null) ? 0 : idProduct.hashCode());
        result = prime * result
                + ((idOrder == null) ? 0 : idOrder.hashCode());
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
