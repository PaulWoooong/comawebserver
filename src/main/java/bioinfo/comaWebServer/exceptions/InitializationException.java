package bioinfo.comaWebServer.exceptions;

public class InitializationException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public InitializationException()
	{
		super("The system has not been initialized yet!");
	}
	
	public InitializationException(String msg)
	{
		super(msg);
	}
}
