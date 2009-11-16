package bioinfo.comaWebServer.dataServices;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.entities.EmailNotification;

public class SMTPMailService implements IMailService
{	
	public void send(String email, String content, String subject) throws Exception
	{
		  EmailNotification params = Cache.getEmailNotificationParams(); 
		
	      Properties props = new Properties();
	      
	      props.put("mail.host", params.getHostname());
	       
	      Session mailConnection = Session.getInstance(props, null);
	      
	      Message msg = new MimeMessage(mailConnection);

	      Address from = new InternetAddress(params.getSender());
	      Address to = new InternetAddress(email);
	    
	      msg.setContent(content, "text/plain");
	      msg.setFrom(from);
	      msg.setRecipient(Message.RecipientType.TO, to);
	      msg.setSubject(subject);
	      
	      Transport.send(msg);
	}

	public void send(String sender, String receiver, String content, String subject) throws Exception 
	{
		  EmailNotification params = Cache.getEmailNotificationParams(); 
		
	      Properties props = new Properties();
	      
	      props.put("mail.host", params.getHostname());
	       
	      Session mailConnection = Session.getInstance(props, null);
	      
	      Message msg = new MimeMessage(mailConnection);
	
	      Address from = new InternetAddress(sender);
	      Address to = new InternetAddress(receiver);
	    
	      msg.setContent(content, "text/plain");
	      msg.setFrom(from);
	      msg.setRecipient(Message.RecipientType.TO, to);
	      msg.setSubject(subject);
	      
	      Transport.send(msg);
		
	}
}
