package modelHibernate;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;  

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "product_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )		
	Long id;
	
    String name;
    float price;
    String image_url;
    
    @ManyToMany(targetEntity = Ingredient.class, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "product_ingredient",
    		joinColumns = {@JoinColumn(name="product_id")},
    		inverseJoinColumns = {@JoinColumn(name ="ingredient_id")})
    List<Ingredient> listIngredients;
        
	@ManyToMany(targetEntity = Type.class, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "product_type",
    		joinColumns = {@JoinColumn(name="product_id")},
    		inverseJoinColumns = {@JoinColumn(name ="type_id")})
    List<Type> listTypes;
	
	@OneToMany(mappedBy = "order")
    //@OneToMany(targetEntity = ProductOrder.class, cascade = {CascadeType.ALL})
	//@JoinColumn(name="idProduct")
	List<ProductOrder> listProductOrder;
	
	@OneToMany(mappedBy = "user")
    List<ReviewProduct> listReviewProduct;

    
    public  Product() { }
    

    public Product(String name, float price, String image_url, List<Ingredient> listIngredienti, List<Type> listTypes,
			List<ProductOrder> listProductOrder, List<ReviewProduct> listReviewProduct) {
		super();
		this.name = name;
		this.price = price;
		this.image_url = image_url;
		this.listIngredienti = listIngredienti;
		this.listTypes = listTypes;
		this.listProductOrder = listProductOrder;
		this.listReviewProduct = listReviewProduct;
	}

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}


	public List<Ingredient> getListIngredients() {
		return listIngredients;
	}

	public void setListIngredients(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}

	public List<Type> getListTypes() {
		return listTypes;
	}

	public void setListTypes(List<Type> listTypes) {
		this.listTypes = listTypes;
	}

	public List<ProductOrder> getListProductOrder() {
		return listProductOrder;
	}

	public void setListProductOrder(List<ProductOrder> listProductOrder) {
		this.listProductOrder = listProductOrder;
	}

	public List<ReviewProduct> getListReviewProduct() {
		return listReviewProduct;
	}

	public void setListReviewProduct(List<ReviewProduct> listReviewProduct) {
		this.listReviewProduct = listReviewProduct;
	}  

	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("name", name);
		obj.put("price", price);
		obj.put("image_url", image_url);

		JSONArray ingredients = new JSONArray();
		
		for(Ingredient i: listIngredients)
		{
			ingredients.put(i.getJson());
		}
		
		obj.put("listIngredients", ingredients);
		
		JSONArray types = new JSONArray();
		
		for(Type t: listTypes)
		{
			types.put(t.getJson());
		}
		
		obj.put("listTypes", types);
		
		JSONArray productorders = new JSONArray();
		
		for(ProductOrder po: listProductOrder)
		{
			productorders.put(po.getJson());
		}
		
		obj.put("listProductOrder", productorders);
		
		JSONArray reviewproducts = new JSONArray();
		
		for(ReviewProduct rp: listReviewProduct)
		{
			reviewproducts.put(rp.getJson());
		}
		
		obj.put("listReviewProduct", reviewproducts);
		
		
		return obj;
	}

}
