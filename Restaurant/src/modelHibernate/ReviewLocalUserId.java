package modelHibernate;

import java.io.Serializable;
import javax.persistence.*;  

@Embeddable
public class ReviewLocalUserId implements Serializable {
	
	@Column(name = "idRestaurant")
	Long idRestaurant;
	
	@Column(name = "NumeroTelefono")
	String NumeroTelefono;
	
	public ReviewLocalUserId() {}
	public ReviewLocalUserId(Long idRestaurant, String NumeroTelefono)
	{
		super();
		this.idRestaurant = idRestaurant;
		this.NumeroTelefono = NumeroTelefono;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumeroTelefono == null) ? 0 : NumeroTelefono.hashCode());
		result = prime * result + ((idRestaurant == null) ? 0 : idRestaurant.hashCode());
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
		ReviewLocalUserId other = (ReviewLocalUserId) obj;
		if (NumeroTelefono == null) {
			if (other.NumeroTelefono != null)
				return false;
		} else if (!NumeroTelefono.equals(other.NumeroTelefono))
			return false;
		if (idRestaurant == null) {
			if (other.idRestaurant != null)
				return false;
		} else if (!idRestaurant.equals(other.idRestaurant))
			return false;
		return true;
	}
	public Long getIdRestaurant() {
		return idRestaurant;
	}
	public void setIdRestaurant(Long idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	public String getNumeroTelefono() {
		return NumeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}
	
}
