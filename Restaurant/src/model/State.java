package model;

public enum State{
	   IN_PREPARAZIONE("In Preparazione"), IN_CONSEGNA("In Consegna"), CONSEGNATO("Consegnato"), PRONTO("Pronto"), RICHIESTO("Richiesto");
	
	private String displayName;
	State(String displayName)
	{
		this.displayName = displayName;
	}
	
	public String displayName() {return displayName;}
}