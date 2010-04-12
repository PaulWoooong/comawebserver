package bioinfo.comaWebServer.exceptions;

public class JobFailureException extends Exception 
{
	private static final long serialVersionUID = 1L;

	public JobFailureException()
	{
		super("Job failure!");
	}
	
	public JobFailureException(String msg)
	{
		super(msg);
	}
}
