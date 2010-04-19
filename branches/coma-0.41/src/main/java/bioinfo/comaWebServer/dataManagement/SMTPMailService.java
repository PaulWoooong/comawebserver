package bioinfo.comaWebServer.dataManagement;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPMailService
{	
	public static synchronized void send(String sender, String receiver, String content, String subject, String hostname) throws Exception 
	{
	      Properties props = new Properties();
	      
	      props.put("mail.host", hostname);
	       
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
