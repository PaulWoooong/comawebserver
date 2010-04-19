package bioinfo.comaWebServer.components;

import nu.localhost.tapestry.acegi.services.LogoutService;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.pages.Index;

@IncludeStylesheet("context:assets/styles.css")
public class LeftMenuAdmin 
{
	@Inject
    private LogoutService logoutService;
	
	@OnEvent(component="logout")
	Object onLogout()
	{
		logoutService.logout();
		
		return Index.class;
	}
}
