package bioinfo.comaWebServer.entities;

public class DataFile 
{
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	
	private long id;
	
	private String path;
	private String type;
	
	public DataFile(){}
	
	public DataFile(String path, String type)
	{
		this.path = path;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
