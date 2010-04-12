package bioinfo.comaWebServer.exceptions;

public class MakingDirException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public MakingDirException()
	{
		super("Failed creating directory!");
	}
	
	public MakingDirException(String msg)
	{
		super(msg);
	}
}
