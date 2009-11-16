package bioinfo.comaWebServer.entities;

import java.util.ArrayList;
import java.util.List;

public class RecentJobs 
{
	private List<String> jobs = null;
	
	public RecentJobs()
	{
		jobs = new ArrayList<String>();
	}

	public List<String> getJobs() {
		return jobs;
	}
	
	public void addJob(String jobId)
	{
		jobs.add(jobId);
	}
	
}
