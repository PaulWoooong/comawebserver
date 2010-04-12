package bioinfo.comaWebServer.pages.show;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;

@IncludeStylesheet("context:assets/styles.css")
public class ShowInfo 
{
	@Persist
	private String action;
	
	@Persist
	private String info;
	
	public void setUp(String info, String action)
	{
		this.action = action;
		this.info	= info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
