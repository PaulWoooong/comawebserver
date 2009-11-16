package bioinfo.comaWebServer.util;

import java.util.Map;

import org.apache.tapestry5.OptionModel;

import bioinfo.comaWebServer.entities.DatabaseItem;

public class DatabaseItemOptionModel implements OptionModel
{
	private DatabaseItem databaseItem;
	
	public DatabaseItemOptionModel(DatabaseItem databaseItem)
	{
		this.databaseItem = databaseItem;
	}
	
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel()
	{
		return databaseItem.getName();
	}

	public Object getValue() 
	{
		return databaseItem;
	}

	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
