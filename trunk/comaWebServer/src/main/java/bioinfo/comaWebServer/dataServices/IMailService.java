package bioinfo.comaWebServer.dataServices;

public interface IMailService 
{
	public void send(String sender, 
					 String receiver, 
					 String content, 
					 String subject) throws Exception;
	
	public void send(String receiver, 
					 String content, 
					 String subject) throws Exception;
}
