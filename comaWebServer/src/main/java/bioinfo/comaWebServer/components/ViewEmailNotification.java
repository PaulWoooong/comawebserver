package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.EmailNotification;

@IncludeStylesheet("context:assets/styles.css")
public class ViewEmailNotification 
{	
	@Parameter(required = true)
	private EmailNotification emailNotification;
	
	public EmailNotification getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(EmailNotification emailNotification) {
		this.emailNotification = emailNotification;
	}
}
