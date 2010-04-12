package bioinfo.comaWebServer.exceptions;

public class FormatException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public FormatException()
	{
		super("Incorrect input format!");
	}
	
	public FormatException(String msg)
	{
		super(msg);
	}
}
