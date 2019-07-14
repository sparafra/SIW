package model;


public class Ingredient {

    Long id;
    String Nome;
    float Prezzo;

    public Ingredient(Long id, String Nome, float Prezzo)
    {
        this.id = id;
        this.Nome = Nome;
        this.Prezzo = Prezzo;
    }
   
    public Ingredient(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
