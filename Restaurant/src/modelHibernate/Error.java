package modelHibernate;

public enum Error{
	   BLANK_SESSION("BLANK_SESSION"), NOT_FOUNDED("NOT_FOUNDED"), COMPLETED("COMPLETED"), GENERIC_ERROR("GENERIC_ERROR"), RICHIESTO("Richiesto");
	
	private String displayName;
	Error(String displayName)
	{
		this.displayName = displayName;
	}
	
	public String displayName() {return displayName;}
}