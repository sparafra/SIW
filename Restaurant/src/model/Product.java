package model;

import java.util.List;

public class Product {

	Long id;
    String Nome;
    float Prezzo;
    String Tipo;
    String ImageURL;
    int Quantita;
    List<Ingredient> listIngredienti;
	List<ReviewProduct> listReview;
    Long idLocale;

    public Product(Long id, String Nome, float Prezzo, String Tipo, String ImageURL, List<Ingredient> listIngredienti)
    {
        this.id = id;
        this.Nome = Nome;
        this.Prezzo = Prezzo;
        this.Tipo = Tipo;
        this.ImageURL = ImageURL;
        this.listIngredienti = listIngredienti;
    }
   
    public  Product()
    {
    	this.Quantita=1;
    }

    public boolean equals(Object object) {
        Product product = (Product) object;
        return (this.getId() == product.getId());
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNome(String nome) {
        Nome = nome;
    }
    public void setPrezzo(float prezzo) {
        Prezzo = prezzo;
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return Nome;
    }
    public float getPrezzo() {
        return Prezzo;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    public List<Ingredient> getListIngredienti() {
        return listIngredienti;
    }
    public void setListIngredienti(List<Ingredient> listIngredienti) {
        this.listIngredienti = listIngredienti;
    }
    public void setImageURL(String ImageURL){this.ImageURL = ImageURL;}
    public String getImageURL(){return ImageURL;}

	public int getQuantita() {
		return Quantita;
	}

	public void setQuantita(int quantita) {
		Quantita = quantita;
	}

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
    
}
