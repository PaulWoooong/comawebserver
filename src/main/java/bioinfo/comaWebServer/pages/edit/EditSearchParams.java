package bioinfo.comaWebServer.pages.edit;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@IncludeStylesheet("context:assets/styles.css")
@Secured("ROLE_ADMIN")
public class EditSearchParams 
{
	@Inject
	private IDataSource dataSource;
	
	private Search search;
	private List<DatabaseItem> sequenceDB;
	private List<DatabaseItem> selectedSequenceDB;
	
	public void onPrepare() throws Exception
	{
		search 		= dataSource.getSearchParams();
		sequenceDB 	= dataSource.getDatabases(DatabaseItem.SEQUENCE_DB);
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	@OnEvent(component="updateSearchForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onSubmitFromUpdateSearchForm()
	{
		String info = dataSource.updateParams(search);
		
		infoPage.setUp(info, "Updating seach params:");
		
		return infoPage;
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public List<DatabaseItem> getSequenceDB() {
		return sequenceDB;
	}

	public void setSequenceDB(List<DatabaseItem> sequenceDB) {
		this.sequenceDB = sequenceDB;
	}

	public List<DatabaseItem> getSelectedSequenceDB() {
		return selectedSequenceDB;
	}

	public void setSelectedSequenceDB(List<DatabaseItem> selectedSequenceDB) {
		this.selectedSequenceDB = selectedSequenceDB;
	}

	public ShowInfo getInfoPage() {
		return infoPage;
	}

	public void setInfoPage(ShowInfo infoPage) {
		this.infoPage = infoPage;
	}
	
	Object onException(Throwable cause)
    {

    	infoPage.setUp("", "We are sorry but managing databases failed!");

        return infoPage;
    }
}
