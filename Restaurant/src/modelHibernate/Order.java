package modelHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;  

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue
    Long id;
	
    String Stato;
    Boolean Asporto;
    //String NumeroTelefono;
    
    //Long idLocale;
    
    //@OneToMany(targetEntity = ProductOrder.class, cascade = {CascadeType.ALL})
    @OneToMany(mappedBy = "product")
    //@JoinColumn(name="idOrder")
    List<ProductOrder> listProductOrder;
    
    
    
    Float Costo;

    Date DateTime;
    
    boolean Pagato;
    
    public Order(){}
    
    public Order(String Stato, Boolean Asporto, List<ProductOrder>listProductOrder, float Costo, Date datetime, Boolean Pagato)
    {
    	this.Stato = Stato;
    	this.Asporto = Asporto;
    	this.listProductOrder = listProductOrder;
    	this.Costo = Costo;
    	this.DateTime = datetime;
    	this.Pagato = Pagato;
    }

    /*
    public void addProduct(Product P)
    {
    	ProductOrder t = new ProductOrder(P, this, 1);
    	listProductOrder.add(t);
    }
    /*
    public Long getIdLocale() {
		return idLocale;
	}


	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
	*/

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
  
    public Date getDateTime() {
        return DateTime;
    }
    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }
    public List<ProductOrder> getListProductOrder() {
       
    	return listProductOrder;
    }
    public void setListProductOrder(List<ProductOrder> listProductOrder) {
       
    	this.listProductOrder = listProductOrder;
    }

   

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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public float getTotaleCosto()
    {
    	if(Costo == null || Costo == 0)
    	{
	        float Tot=0;
	        for(ProductOrder PO : listProductOrder)
	        {	        		        
	        	Tot += PO.getProduct().getPrezzo() *PO.getQuantity() ;
	            
	        }
	        return  Tot;
	    	}
    	else
    		return Costo;
    }
    
    public void setCosto(Float Costo) {
    	this.Costo = Costo;
    }


	public boolean getPagato() {
		return Pagato;
	}


	public void setPagato(boolean pagato) {
		Pagato = pagato;
	}
    
    
}
