package modelHibernate;

import java.util.List;
import javax.persistence.*;  

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long Id;
	
    String Nome;
    float Prezzo;
    String ImageURL;
    
    @ManyToMany(targetEntity = Ingredient.class, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "product_ingredient",
    		joinColumns = {@JoinColumn(name="idProduct")},
    		inverseJoinColumns = {@JoinColumn(name ="idIngredient")})
    List<Ingredient> listIngredienti;
    
	//List<ReviewProduct> listReview;
    
	@ManyToMany(targetEntity = Type.class, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "product_type",
    		joinColumns = {@JoinColumn(name="idProduct")},
    		inverseJoinColumns = {@JoinColumn(name ="idType")})
    List<Type> listTypes;
	
	@OneToMany(mappedBy = "order")
    //@OneToMany(targetEntity = ProductOrder.class, cascade = {CascadeType.ALL})
	//@JoinColumn(name="idProduct")
	List<ProductOrder> listProductOrder;
	
	@OneToMany(mappedBy = "user")
    List<ReviewProduct> listReviewProduct;
	
	//Long idLocale;

    public Product(Long id, String Nome, float Prezzo, String Tipo, String ImageURL, List<Ingredient> listIngredienti)
    {
        this.Id = id;
        this.Nome = Nome;
        this.Prezzo = Prezzo;
        this.ImageURL = ImageURL;
        this.listIngredienti = listIngredienti;
    }
    public Product(String Nome, float Prezzo, List<Type> listTypes, String ImageURL, List<Ingredient> listIngredienti)
    {
        this.Nome = Nome;
        this.Prezzo = Prezzo;
        this.listTypes = listTypes;
        this.ImageURL = ImageURL;
        this.listIngredienti = listIngredienti;
    }
    public  Product()
    {
    	
    }

    public boolean equals(Object object) {
        Product product = (Product) object;
        return (this.getId() == product.getId());
    }
    public void setId(Long id) {
        this.Id = id;
    }
    public void setNome(String nome) {
        Nome = nome;
    }
    public void setPrezzo(float prezzo) {
        Prezzo = prezzo;
    }
    public Long getId() {
        return Id;
    }
    public String getNome() {
        return Nome;
    }
    public float getPrezzo() {
        return Prezzo;
    }

    public List<Ingredient> getListIngredienti() {
        return listIngredienti;
    }
    public void setListIngredienti(List<Ingredient> listIngredienti) {
        this.listIngredienti = listIngredienti;
    }
    public void setImageURL(String ImageURL){this.ImageURL = ImageURL;}
    public String getImageURL(){return ImageURL;}
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

	
    /*
	public List<ReviewProduct> getListReview() {
		return listReview;
	}

	public void setListReview(List<ReviewProduct> listReview) {
		this.listReview = listReview;
	}
	
	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
    */
}
