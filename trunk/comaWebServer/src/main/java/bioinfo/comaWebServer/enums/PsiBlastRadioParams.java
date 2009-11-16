package bioinfo.comaWebServer.enums;

public enum PsiBlastRadioParams 
{	
	RR 	("RR=1"), //restart psi-blast with msa
	NR	("RR=0"), //do not use psi-blast
	IN 	("IN=1"); //let the server handle

	private final String value;
	
	PsiBlastRadioParams(String ext)
	{
		this.value = ext;
	}
	
	public String getValue()
	{
		return value;
	}
}
