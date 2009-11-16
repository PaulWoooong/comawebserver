package bioinfo.comaWebServer.entities;

import java.io.Serializable;

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
