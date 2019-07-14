package model;

import java.util.Date;

public class ReviewLocal extends Review {

	Long idLocale;
	String Recensione;
	
	public ReviewLocal(Long idLocale, String NumeroTelefono, int Voto, String Recensione, Date DataOra)
	{
		super(NumeroTelefono, Voto, DataOra);
		this.idLocale = idLocale;
		this.Recensione = Recensione;
	}
	public ReviewLocal() {}
	
	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}

	public String getRecensione() {
		return Recensione;
	}

	public void setRecensione(String recensione) {
		Recensione = recensione;
	}
	
	
}
