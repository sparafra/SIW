package model;

public class Notice {

	Long idAvviso;
	boolean Stato;
	String CreatoDa;
	String Messaggio;
	Long idLocale;
	String RicevutoDa;
	
	public Notice() {}
	
	public Notice(Long idAvviso, boolean Stato, String CreatoDa, String Messaggio, Long idLocale, String RicevutoDa)
	{
		this.idAvviso = idAvviso;
		this.Stato = Stato;
		this.CreatoDa = CreatoDa;
		this.Messaggio = Messaggio;
		this.idLocale = idLocale;
		this.RicevutoDa = RicevutoDa;
		
	}

	public Long getIdAvviso() {
		return idAvviso;
	}

	public void setIdAvviso(Long idAvviso) {
		this.idAvviso = idAvviso;
	}

	public boolean getStato() {
		return Stato;
	}

	public void setStato(boolean stato) {
		Stato = stato;
	}

	public String getCreatoDa() {
		return CreatoDa;
	}

	public void setCreatoDa(String creatoDa) {
		CreatoDa = creatoDa;
	}

	public String getMessaggio() {
		return Messaggio;
	}

	public void setMessaggio(String messaggio) {
		Messaggio = messaggio;
	}

	public Long getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(Long idLocale) {
		this.idLocale = idLocale;
	}

	public String getRicevutoDa() {
		return RicevutoDa;
	}

	public void setRicevutoDa(String ricevutoDa) {
		RicevutoDa = ricevutoDa;
	}
	
	
	
	
}
