package bioinfo.comaWebServer.exceptions;

public class SSHConnectioException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;

	public SSHConnectioException()
	{
		super("Can't establish SSH connection with a workstation!");
	}
	
	public SSHConnectioException(String msg)
	{
		super(msg);
	}
}
