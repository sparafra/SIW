package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    Long id;
    String Stato;
    Boolean Asporto;
    String NumeroTelefono;
    Long idLocale;
    List<Product> listProducts;
    Float Costo;

    Date DateTime;
    
    boolean Pagato;
    public Order(){listProducts = new ArrayList<>(); Costo = null; Pagato = false; }


    public void addProduct(Product P)
    {
        listProducts.add(P);
    }

    public Long getIdLocale() {
		return idLocale;
	}


	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}


	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStato() {
        return Stato;
    }
    public void setStato(String stato) {
        Stato = stato;
    }
    public boolean getAsporto() {
        return Asporto;
    }
    public void setAsporto(Boolean asporto) {
        Asporto = asporto;
    }
    public String getNumeroTelefono() {
        return NumeroTelefono;
    }
    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }
    public Date getDateTime() {
        return DateTime;
    }
    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }
    public List<Product> getListProducts() {
        return listProducts;
    }
    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    @Override
    public boolean equals(Object obj) {
        Order o = (Order) obj;
        return (this.id == o.getId());
    }

    public float getTotaleCosto()
    {
    	if(Costo == null || Costo == 0)
    	{
	        float Tot=0;
	        for(int k=0; k<listProducts.size(); k++)
	        {
	            
	            if(listProducts.get(k).getQuantita() > 1)
	            {
	            	Tot += listProducts.get(k).getPrezzo() *listProducts.get(k).getQuantita() ;
	            }
	            else
	            {
	            	Tot += listProducts.get(k).getPrezzo();
	            }
	        }
	        return  Tot;
	    	}
    	else
    		return Costo;
    }
    
    public void setCosto(Float Costo) {this.Costo = Costo;}


	public boolean getPagato() {
		return Pagato;
	}


	public void setPagato(boolean pagato) {
		Pagato = pagato;
	}
    
    
}
