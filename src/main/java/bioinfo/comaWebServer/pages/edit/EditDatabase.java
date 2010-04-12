package bioinfo.comaWebServer.pages.edit;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.pages.show.ShowDatabases;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@IncludeStylesheet("context:assets/styles.css")
@Secured("ROLE_ADMIN")
public class EditDatabase 
{
	@Persist
	private DatabaseItem database;
	
	public void setUp(long id) throws Exception
	{
		database = dataSource.getDatabase(id);
	}
	
	@Inject
	private IDataSource dataSource;

	@OnEvent(component="EditDatabaseForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onSubmitFromEditDatabaseForm() throws Exception
	{
		dataSource.saveOrUpdate(database);
		return ShowDatabases.class;
	}

	public DatabaseItem getDatabase() 
	{
		return database;
	}

	public void setDatabase(DatabaseItem database) 
	{
		this.database = database;
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	Object onException(Throwable cause)
    {

    	infoPage.setUp("", "We are sorry but managing databases failed!");

        return infoPage;
    }

}

