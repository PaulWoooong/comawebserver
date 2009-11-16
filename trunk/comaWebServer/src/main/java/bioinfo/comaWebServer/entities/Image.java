package bioinfo.comaWebServer.entities;

import java.io.Serializable;

public class Image implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String path;
	private int priority;
	
	public Image(){}
	
	public Image(String path, int priority)
	{
		this.path = path;
		this.priority = priority;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
