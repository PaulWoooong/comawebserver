package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.DatabaseItem;

@IncludeStylesheet("context:assets/styles.css")
public class ViewDatabase 
{
	private String type1 = DatabaseItem.SEQUENCE_DB;
	private String type2 = DatabaseItem.PROFILE_DB;
	
	@Parameter(required = true)
	private DatabaseItem database;

	public DatabaseItem getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseItem database) {
		this.database = database;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}
}
