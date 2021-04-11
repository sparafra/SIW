package modelHibernate;

import java.util.List;
import javax.persistence.*;    

@Entity
@Table(name="ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long Id;
	
    String Nome;
    float Prezzo;
    
    @ManyToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
    @JoinTable(
    		name = "product_ingredient",
    		joinColumns = {@JoinColumn(name="idIngredient")},
    		inverseJoinColumns = {@JoinColumn(name ="idProduct")})
    List<Product> listProducts;
    
    
    public Ingredient(Long id, String Nome, float Prezzo)
    {
        this.Id = id;
        this.Nome = Nome;
        this.Prezzo = Prezzo;
    }
    public Ingredient(String Nome, float Prezzo)
    {
        this.Nome = Nome;
        this.Prezzo = Prezzo;
    }
   
    public Ingredient(){}

    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        this.Id = id;
    }
    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }
    public float getPrezzo() {
        return Prezzo;
    }
    public void setPrezzo(float prezzo) {
        Prezzo = prezzo;
    }
}
