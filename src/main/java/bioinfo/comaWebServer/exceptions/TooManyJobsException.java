package bioinfo.comaWebServer.exceptions;

public class TooManyJobsException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public TooManyJobsException()
	{
		super("There are too many submitted jobs!");
	}
	
	public TooManyJobsException(String msg)
	{
		super(msg);
	}

}
