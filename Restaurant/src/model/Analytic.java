package model;

import java.util.Date;

public class Analytic {

	Long idView;
	String Pagina;
	Date DataOra;
	String NumeroTelefono;
	Long idLocale;
	
	public Analytic()
	{
		
	}

	public Long getIdView() {
		return idView;
	}

	public void setIdView(Long idView) {
		this.idView = idView;
	}

	public String getPagina() {
		return Pagina;
	}

	public void setPagina(String pagina) {
		Pagina = pagina;
	}

	public Date getDataOra() {
		return DataOra;
	}

	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}

	public String getNumeroTelefono() {
		return NumeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}

	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
	
	
}
