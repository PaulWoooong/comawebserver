package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.util.EnumSelectModel;

import bioinfo.comaWebServer.entities.Alignment;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.enums.Scheme;
import bioinfo.comaWebServer.util.GenericMultiValueEncoder;
import bioinfo.comaWebServer.util.GenericSelectionModel;

public class ViewAlignmentMultiple 
{		
	@Parameter(required=true)
	private List<DatabaseItem> alignmentDB;
	
	@Parameter(required=true)
	private List<DatabaseItem> selectedAlignmentDB;
	
	@Inject
    private PropertyAccess access;

	
	public GenericSelectionModel<DatabaseItem> getDatabaseSelectModel() 
	{
    		return new GenericSelectionModel<DatabaseItem>(alignmentDB, "name", access);
	}

	public GenericMultiValueEncoder<DatabaseItem> getDatabaseValueEncoder() 
	{
		return new GenericMultiValueEncoder<DatabaseItem>(selectedAlignmentDB, "id");
	}
	
	public List<DatabaseItem> getAlignmentDB() {
		return alignmentDB;
	}

	public void setAlignmentDB(List<DatabaseItem> alignmentDB) {
		this.alignmentDB = alignmentDB;
	}

	public List<DatabaseItem> getSelectedAlignmentDB() {
		return selectedAlignmentDB;
	}

	public void setSelectedAlignmentDB(List<DatabaseItem> selectedAlignmentDB) {
		this.selectedAlignmentDB = selectedAlignmentDB;
	}

	public PropertyAccess getAccess() {
		return access;
	}

	public void setAccess(PropertyAccess access) {
		this.access = access;
	}
	
	@Parameter(required = true)
	private Alignment alignment;

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	
	@Inject
	private Messages messages;
	public SelectModel getSchemes()
	{
	  return new EnumSelectModel(Scheme.class, messages);
	}
	
	@Parameter(required = true)
	private boolean showAlignment;

	public boolean isShowAlignment() {
		return showAlignment;
	}

	public void setShowAlignment(boolean showAlignment) {
		this.showAlignment = showAlignment;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
}
