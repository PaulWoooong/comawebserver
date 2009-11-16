package bioinfo.comaWebServer.entities;

import java.io.Serializable;

public class ScopBibliography  implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String scopId;
	private String scopHierarchy;
	
	public ScopBibliography(){}
	
	public ScopBibliography(String id, String hierarchy)
	{
		scopId = id;
		scopHierarchy = hierarchy;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScopId() {
		return scopId;
	}
	public void setScopId(String scopId) {
		this.scopId = scopId;
	}
	public String getScopHierarchy() {
		return scopHierarchy;
	}
	public void setScopHierarchy(String scopHierarchy) {
		this.scopHierarchy = scopHierarchy;
	}
}
