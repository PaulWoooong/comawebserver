package bioinfo.comaWebServer.util;

import org.apache.tapestry5.ValueEncoder;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;

public class DatabaseItemEncoder implements ValueEncoder<DatabaseItem>
{
	private IDataSource dataSource;
	
	public DatabaseItemEncoder(IDataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public DatabaseItem toValue(String id) 
	{
		try 
		{
			return dataSource.getDatabase(Long.parseLong(id));
		} 
		catch (Exception e) {}
		
		return null;
	}

	public String toClient(DatabaseItem obj) 
	{
		return "" + obj.getId();
	}

}
