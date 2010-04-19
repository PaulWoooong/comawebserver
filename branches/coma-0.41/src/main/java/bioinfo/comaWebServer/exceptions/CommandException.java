package bioinfo.comaWebServer.exceptions;

public class CommandException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public CommandException(String msg)
	{
		super(msg);
	}
	
	public CommandException()
	{
		super("Failed to execute remote command!");
	}
}
