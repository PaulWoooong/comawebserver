package bioinfo.comaWebServer.pages.edit;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.ComaParams;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@Secured("ROLE_ADMIN")
public class EditComaParams 
{	
	@Inject
	private IDataSource dataSource;

	@Property
	@Persist
	private ComaParams comaParams;
	
	@Persist
	private List<DatabaseItem> alignmentDB;
	@Persist
	private List<DatabaseItem> selectedAlignmentDB;

	public void onPrepare() throws Exception
	{
		comaParams				= dataSource.getComaParams();
		alignmentDB				= dataSource.getDatabases(DatabaseItem.PROFILE_DB);
	}
	
	@InjectPage
	private ShowInfo infoPage;
	private boolean initInfoPage;
	
	@OnEvent(component="updateCOMAParamsForm", value = "submit")
	Object onUpdateCOMAParamsForm()
	{
		if(initInfoPage)
		{
			return infoPage;
		}
		return null;
	}
	
	@OnEvent(component="updateComaParamsButton", value = "selected")
	@Secured("ROLE_ADMIN")
	void onUpdateComaParamsButton()
	{
		infoPage.setUp("", "Updating COMA params: OK");
		
		try 
		{
			dataSource.update(comaParams);
		} 
		catch (Exception e) 
		{
			infoPage.setUp("", "Updating COMA params: Failed");
		}
		
		initInfoPage = true;
	}

	public List<DatabaseItem> getAlignmentDB() {
		return alignmentDB;
	}

	public void setAlignmentDB(List<DatabaseItem> alignmentDB) {
		this.alignmentDB = alignmentDB;
	}

	public List<DatabaseItem> getSelectedAlignmentDB() {
		return selectedAlignmentDB;
	}

	public void setSelectedAlignmentDB(List<DatabaseItem> selectedAlignmentDB) {
		this.selectedAlignmentDB = selectedAlignmentDB;
	}
	
	 void onException(Throwable cause){}
}
