package model;

import java.util.Date;
import java.util.List;

public abstract class Review {

	
	String NumeroTelefono;
	
	int Voto;
		
	Date DataOra;
	
    public Review(String NumeroTelefono, int Voto, Date DataOra)
    {
    
        this.NumeroTelefono = NumeroTelefono;
        this.Voto = Voto;
        this.DataOra = DataOra;
    }
   
    public Review()
    {
    	
    }

	public String getNumeroTelefono() {
		return NumeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}

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
    
    
}
    