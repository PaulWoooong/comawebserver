package bioinfo.comaWebServer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.util.AbstractSelectModel;

import bioinfo.comaWebServer.entities.DatabaseItem;

public class DatabaseItemSelectModel extends AbstractSelectModel 
{
	private List<DatabaseItem> databaseItems;
	
	public DatabaseItemSelectModel(List<DatabaseItem> databaseItems)
	{
		this.databaseItems = databaseItems;
	}
	
	public List<OptionGroupModel> getOptionGroups() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<OptionModel> getOptions() 
	{
		List<OptionModel> list = new ArrayList<OptionModel>();
		
		for(DatabaseItem d: databaseItems)
		{
			list.add(new DatabaseItemOptionModel(d));
		}
			
		return list;
	}

}
