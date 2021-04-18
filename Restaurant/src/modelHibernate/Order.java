package modelHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;  

@Entity
@Table(name="order")
public class Order {
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "order_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )		
    Long id;
	
    String state;
    Boolean take_away;
    Float price;
    Date date_time;  
    boolean paid;
    
    //@OneToMany(targetEntity = ProductOrder.class, cascade = {CascadeType.ALL})
    @OneToMany(mappedBy = "product")
    List<ProductOrder> listProductOrder;

    
    public Order(){}
    

	public Order(String state, Boolean take_away, List<ProductOrder> listProductOrder, Float price, Date date_time,
			boolean paid) {
		super();
		this.state = state;
		this.take_away = take_away;
		this.listProductOrder = listProductOrder;
		this.price = price;
		this.date_time = date_time;
		this.paid = paid;
	}

	
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getTake_away() {
		return take_away;
	}

	public void setTake_away(Boolean take_away) {
		this.take_away = take_away;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public List<ProductOrder> getListProductOrder() {
		return listProductOrder;
	}

	public void setListProductOrder(List<ProductOrder> listProductOrder) {
		this.listProductOrder = listProductOrder;
	}

	public float getTotaleCosto()
    {
    	if(price == null || price == 0)
    	{
	        float Tot=0;
	        for(ProductOrder PO : listProductOrder)
	        {	        		        
	        	Tot += PO.getProduct().getPrice() *PO.getQuantity() ;
	            
	        }
	        return  Tot;
	    	}
    	else
    		return price;
    }

    
}
