package modelHibernate;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;  

@Embeddable
public class ReviewProductUserId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
	Long product_id;
	
	@Column(name = "telephone")
	String telephone;
	
	public ReviewProductUserId() {}
	public ReviewProductUserId(Long product_id, String telephone)
	{
		super();
		this.product_id = product_id;
		this.telephone = telephone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
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
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (product_id == null) {
			if (other.product_id != null)
				return false;
		} else if (!product_id.equals(other.product_id))
			return false;
		return true;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
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

		obj.put("product_id", product_id);
		obj.put("telephone", telephone);
		
		
		return obj;
	}
	
	
}
