package bioinfo.comaWebServer.entities;

import java.io.Serializable;

public class DatabaseItem extends AbstractParameter implements Serializable
{
	private static final long serialVersionUID	= 1L;
	public static String SEQUENCE_DB 			 	= "SEQUENCE DATABASE";
	public static String PROFILE_DB 			 	= "PROFILE  DATABASE";
	
	private long id;
	private String name;
	private String type = SEQUENCE_DB;
	private String path;
	private String value;
	private boolean selected;
	
	private String partialName;
	private boolean noModeling;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public DatabaseItem(long id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public DatabaseItem(String name, String type, String path)
	{
		this.type = type;
		this.name = name;
		this.path = path;
	}
	
	public DatabaseItem(){}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseItem other = (DatabaseItem) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getValues() 
	{
		if(value == null)
		{
			if(path == null)
			{
				return name;
			}
			else
			{
				return path;
			}
		}
		
		return value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPartialName() {
		return partialName;
	}
	public void setPartialName(String partialName) {
		this.partialName = partialName;
	}
	public boolean isNoModeling() {
		return noModeling;
	}
	public void setNoModeling(boolean noModeling) {
		this.noModeling = noModeling;
	}

}
