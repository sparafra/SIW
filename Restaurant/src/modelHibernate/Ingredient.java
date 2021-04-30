package modelHibernate;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;    

@Entity
@Table(name="ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "ingredient_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )	
	Long id;
	
    String name;
    float price;
    
    @ManyToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
    @JoinTable(
    		name = "product_ingredient",
    		joinColumns = {@JoinColumn(name="ingredient_id")},
    		inverseJoinColumns = {@JoinColumn(name ="product_id")})
    List<Product> listProducts;
    
    
   
    public Ingredient(String name, float price)
    {
    	this.name = name;
        this.price = price;
    }
   
    public Ingredient(){}
	
    // Getters and Setters
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public List<Product> getListProducts() {
		return listProducts;
	}
	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("name", name);
		obj.put("price", price);
		
		JSONArray products = new JSONArray();
		
		for(Product p: listProducts)
		{
			products.put(p.getJson());
		}
		
		obj.put("listProducts", listProducts);
		
		
		return obj;
	}
}
