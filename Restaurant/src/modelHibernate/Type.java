package modelHibernate;
import java.util.List;

import javax.persistence.*;  

@Entity
@Table(name="type")
public class Type {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long Id;
	String Type;
	
	@ManyToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
	 @JoinTable(
	    		name = "product_type",
	    		joinColumns = {@JoinColumn(name="idType")},
	    		inverseJoinColumns = {@JoinColumn(name ="idProduct")})
	List<Product> listProducts;

	public Type(String Type)
	{
		this.Type = Type;
	}
	public Type()
	{
		
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		Type other = (Type) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
	
}
