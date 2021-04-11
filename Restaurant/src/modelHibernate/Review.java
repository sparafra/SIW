package modelHibernate;

import java.util.Date;
import java.util.List;
import javax.persistence.*;  

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@MappedSuperclass
public class Review {

	
	@ManyToOne
	@MapsId("NumeroTelefono")
	@JoinColumn(name = "NumeroTelefono")
	User user;
	//String NumeroTelefono;
	
	int Voto;
		
	Date DataOra;
	
    public Review(User user, int Voto, Date DataOra)
    {
    
        this.user = user;
        this.Voto = Voto;
        this.DataOra = DataOra;
    }
   
    public Review()
    {
    	
    }
    /*
	public String getNumeroTelefono() {
		return NumeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}
	*/
	public int getVoto() {
		return Voto;
	}

	public void setVoto(int voto) {
		Voto = voto;
	}

	public Date getDataOra() {
		return DataOra;
	}

	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
    