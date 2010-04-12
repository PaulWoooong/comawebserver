package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;


@IncludeStylesheet("context:assets/styles.css")
public class Border 
{
	@Parameter(required = true, defaultPrefix = "literal")
	private String pageTitle;
	public String getPageTitle()
	{
	  return pageTitle;
	}
}
