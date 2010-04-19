package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.util.EnumSelectModel;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.enums.CompositionScore;
import bioinfo.comaWebServer.util.DatabaseItemEncoder;
import bioinfo.comaWebServer.util.DatabaseItemSelectModel;
import bioinfo.comaWebServer.util.GenericMultiValueEncoder;
import bioinfo.comaWebServer.util.GenericSelectionModel;

@IncludeStylesheet("context:assets/styles.css")
public class ViewSearchMultiple 
{
	@Inject
	private IDataSource dataSource;
	
	@Parameter(required=true)
	private Search search;
	
	@Parameter(required=true)
	private List<DatabaseItem> sequenceDB;
	
	public SelectModel getSelectModel()
	{
		return new DatabaseItemSelectModel(sequenceDB);
	}
	
	public ValueEncoder<DatabaseItem> getDatabaseItemEncoder()
	{
		return new DatabaseItemEncoder(dataSource);
	}
	
	@Parameter(required=true)
	private List<DatabaseItem> selectedSequenceDB;
	
	@Inject
    private PropertyAccess access;

	
	public GenericSelectionModel<DatabaseItem> getDatabaseSelectModel() 
	{
    		return new GenericSelectionModel<DatabaseItem>(sequenceDB, "name", access);
	}

	public GenericMultiValueEncoder<DatabaseItem> getDatabaseValueEncoder() 
	{
		return new GenericMultiValueEncoder<DatabaseItem>(sequenceDB, "id");
	}
	
	@Inject
	private Messages messages;
	public SelectModel getCompositionScores()
	{
	  return new EnumSelectModel(CompositionScore.class, messages);
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

	public PropertyAccess getAccess() {
		return access;
	}

	public void setAccess(PropertyAccess access) {
		this.access = access;
	}

}
