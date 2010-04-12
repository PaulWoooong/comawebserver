package bioinfo.comaWebServer.enums;

public enum ParamType 
{
	ADMIN 	("admin"),
	USER 	("user");

	private final String type;
	
	ParamType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}
