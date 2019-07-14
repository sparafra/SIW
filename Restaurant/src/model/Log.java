package model;

import java.util.Date;

public class Log {

	Long idLog;
	String Evento;
	String NumeroTelefono;
	Long idLocale;
	Date DataOra;
	
	
	public Log()
	{
		
	}


	public Long getIdLog() {
		return idLog;
	}


	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}


	public String getEvento() {
		return Evento;
	}


	public void setEvento(String evento) {
		Evento = evento;
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


	public Date getDataOra() {
		return DataOra;
	}


	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}
	
	
}
