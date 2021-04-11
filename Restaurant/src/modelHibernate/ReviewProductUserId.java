package modelHibernate;

import java.io.Serializable;
import javax.persistence.*;  

@Embeddable
public class ReviewProductUserId implements Serializable {
	
	@Column(name = "idProduct")
	Long idProduct;
	
	@Column(name = "NumeroTelefono")
	String NumeroTelefono;
	
	public ReviewProductUserId() {}
	public ReviewProductUserId(Long idProduct, String NumeroTelefono)
	{
		super();
		this.idProduct = idProduct;
		this.NumeroTelefono = NumeroTelefono;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumeroTelefono == null) ? 0 : NumeroTelefono.hashCode());
		result = prime * result + ((idProduct == null) ? 0 : idProduct.hashCode());
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
		ReviewProductUserId other = (ReviewProductUserId) obj;
		if (NumeroTelefono == null) {
			if (other.NumeroTelefono != null)
				return false;
		} else if (!NumeroTelefono.equals(other.NumeroTelefono))
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		return true;
	}
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	public String getNumeroTelefono() {
		return NumeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}
	
	
	
}
