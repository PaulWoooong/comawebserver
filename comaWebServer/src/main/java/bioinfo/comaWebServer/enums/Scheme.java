package bioinfo.comaWebServer.enums;

public enum Scheme 
{
	PROFILE ("profile");
//	GLOBAL  ("global");
	
	private final String scheme;
	
	Scheme(String scheme)
	{
		this.scheme = scheme;
	}
	
	public String getScheme()
	{
		return scheme;
	}
	
}
