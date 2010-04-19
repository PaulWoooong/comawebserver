package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Alignment;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.enums.Scheme;
import bioinfo.comaWebServer.util.DatabaseItemEncoder;
import bioinfo.comaWebServer.util.DatabaseItemSelectModel;

public class ViewAlignment 
{		
	@Inject
	private IDataSource dataSource;
	
	@Parameter(required = true)
	private Alignment alignment;
	
	@Parameter(required = true)
	private boolean showAlignment;
	
	@Parameter(required=true)
	private List<DatabaseItem> alignmentDB;
	
	@Inject
	private Messages messages;
	public SelectModel getSchemes()
	{
	  return new EnumSelectModel(Scheme.class, messages);
	}

	public SelectModel getDatabaseItemSelectModel()
	{
		return new DatabaseItemSelectModel(alignmentDB);
	}
	
	public ValueEncoder<DatabaseItem> getDatabaseItemValueEncoder()
	{
		return new DatabaseItemEncoder(dataSource);
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public boolean isShowAlignment() {
		return showAlignment;
	}

	public void setShowAlignment(boolean showAlignment) {
		this.showAlignment = showAlignment;
	}

	public List<DatabaseItem> getAlignmentDB() {
		return alignmentDB;
	}

	public void setAlignmentDB(List<DatabaseItem> alignmentDB) {
		this.alignmentDB = alignmentDB;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

}
