package bioinfo.comaWebServer.pages.add;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.pages.show.ShowDatabases;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@IncludeStylesheet("context:assets/styles.css")
@Secured("ROLE_ADMIN")
public class AddDatabase 
{
	private DatabaseItem database;
	
	@Inject
	private IDataSource dataSource;
	
	@OnEvent(component="addDatabaseForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onSubmitFromAddDatabaseForm() throws Exception
	{
		if(database.getName() == null || database.getName().equals(""))
		{
			if(database.getPartialName() != null)
			{
				database.setName(database.getPartialName());
			}
			else
			{
				database.setName(database.getValue());
			}
		}
		
		dataSource.saveOrUpdate(database);
		return ShowDatabases.class;
	}

	public DatabaseItem getDatabase() 
	{
		if(database == null)
		{
			database = new DatabaseItem();
		}
		return database;
	}

	public void setDatabase(DatabaseItem database) 
	{
		this.database = database;
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	Object onException(Throwable cause)
    {

    	infoPage.setUp("", "We are sorry but managing databases failed!");

        return infoPage;
    }
}
