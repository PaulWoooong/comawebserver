package bioinfo.comaWebServer.pages.edit;

import org.acegisecurity.annotation.Secured;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;


import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.dataManagement.SMTPMailService;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.EmailNotification;
import bioinfo.comaWebServer.entities.User;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@IncludeStylesheet("context:assets/styles.css")
@Secured("ROLE_ADMIN")
public class EditMailParams 
{
	@Inject
	private IDataSource dataSource;
	
	@Persist
	private EmailNotification emailNotification;
	
	public void onPrepare()
	{
		emailNotification = Cache.getEmailNotificationParams();
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	@OnEvent(component="updateEmailNotificationForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onUpdateEmailNotificationForm()
	{
		dataSource.updateParams(emailNotification);
		Cache.refreshEmailNotificationParams();
		
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String info = "Congratulation message sent!";
		
		try 
		{
			SMTPMailService.send(Cache.getEmailNotificationParams().getSender(), currentUser.getEmail(), 
					"Test email", "coma server", Cache.getEmailNotificationParams().getHostname());
		} 
		catch (Exception e) 
		{
			info = "Congratulation message sending failed!";
		}
		
		infoPage.setUp(info, "Updating Email Notification params:");

		return infoPage;
	}

	public EmailNotification getEmailNotification() 
	{
		if(emailNotification == null)
		{
			emailNotification = new EmailNotification();
		}
		return emailNotification;
	}

	public void setEmailNotification(EmailNotification emailNotification) {
		this.emailNotification = emailNotification;
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
