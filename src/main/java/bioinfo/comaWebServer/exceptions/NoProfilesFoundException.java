package bioinfo.comaWebServer.exceptions;

public class NoProfilesFoundException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public NoProfilesFoundException()
	{
		super("NoProfilesFoundException");
	}

	public NoProfilesFoundException(String msg)
	{
		super(msg);
	}
}
