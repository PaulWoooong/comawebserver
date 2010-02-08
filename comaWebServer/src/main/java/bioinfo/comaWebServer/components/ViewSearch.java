package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.enums.CompositionScore;
import bioinfo.comaWebServer.enums.PsiBlastFilters;
import bioinfo.comaWebServer.enums.PsiBlastRadioParams;
import bioinfo.comaWebServer.util.DatabaseItemEncoder;
import bioinfo.comaWebServer.util.DatabaseItemSelectModel;

@IncludeJavaScriptLibrary("ViewSearch.js")
@IncludeStylesheet("context:assets/styles.css")
public class ViewSearch 
{
	@Inject
	private IDataSource dataSource;
	
	@Parameter(required=true)
	private Search search;
	
	@Parameter(required=true)
	private List<DatabaseItem> sequenceDB;
	
	@Inject
	private Messages messages;
	public SelectModel getCompositionScores()
	{
	  return new EnumSelectModel(CompositionScore.class, messages);
	}
	
	public SelectModel getPsiBlastFilters()
	{
	  return new EnumSelectModel(PsiBlastFilters.class, messages);
	}

	public SelectModel getDatabaseItemSelectModel()
	{
		return new DatabaseItemSelectModel(sequenceDB);
	}
	
	public ValueEncoder<DatabaseItem> getDatabaseItemValueEncoder()
	{
		return new DatabaseItemEncoder(dataSource);
	}
	
	public PsiBlastRadioParams getNotRestart()
	{
		return PsiBlastRadioParams.NR;
	}
	
	public PsiBlastRadioParams getRestart()
	{
		return PsiBlastRadioParams.RR;
	}
	
	public PsiBlastRadioParams getServerHandle()
	{
		return PsiBlastRadioParams.IN;
	}

	public Search getSearch() 
	{
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
}
