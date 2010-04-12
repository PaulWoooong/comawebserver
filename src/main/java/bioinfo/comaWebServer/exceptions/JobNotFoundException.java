package bioinfo.comaWebServer.exceptions;

public class JobNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public JobNotFoundException()
	{
		super("A job was not found. It may have been expired.");
	}
	
	public JobNotFoundException(String msg)
	{
		super(msg);
	}

}
