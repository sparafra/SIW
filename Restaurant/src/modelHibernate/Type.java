package modelHibernate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;  

@Entity
@Table(name="type")
public class Type {
	
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "type_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )	
	Long id;
	String name;
	
	@ManyToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
	 @JoinTable(
	    		name = "product_type",
	    		joinColumns = {@JoinColumn(name="type_id")},
	    		inverseJoinColumns = {@JoinColumn(name ="product_id")})
	List<Product> listProducts;

	public Type(String name)
	{
		this.name = name;
	}
	public Type() { }
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Product> getListProducts() {
		return listProducts;
	}
	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}
	
	//Hashcode and equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("name", name);
		
		JSONArray products = new JSONArray();
		
		for(Product p: listProducts)
		{
			products.put(p.getJson());
		}
		
		obj.put("listProducts", listProducts);
		
		
		return obj;
	}
}
