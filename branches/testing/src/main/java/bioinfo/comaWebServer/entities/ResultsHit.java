package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsHit implements Serializable
{

	private static final long serialVersionUID = 1L;
	private long id;
	private long comaResultsId;
	private String name;
	private String score;
	private String evalue;
	private int priority;
	
	public String toString()
	{
		String info = "comaResultsId " + comaResultsId + 
					  " name " + name +
					  " score " + score +
					  " evalue " + evalue;
		
		return info;
	}
	
	public String getFilteredName() 
	{
		if(name != null)
		{
			Pattern p = Pattern.compile(".*(PF\\d+)\\.\\d+.*");
			Matcher m = p.matcher(name);
			if(m.matches())
			{
				String pfamID = m.group(1);
				return name.replaceFirst("PF\\d+\\.\\d+", pfamID);
			}
		}
		return name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getEvalue() {
		return evalue;
	}
	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getComaResultsId() {
		return comaResultsId;
	}

	public void setComaResultsId(long comaResultsId) {
		this.comaResultsId = comaResultsId;
	}
}
