package modelHibernate;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long idLog;
	
	String Evento;
	//String NumeroTelefono;
	//Long idLocale;
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


	/*
	public Long getIdLocale() {
		return idLocale;
	}


	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}
*/

	public Date getDataOra() {
		return DataOra;
	}


	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}
	
	
}
