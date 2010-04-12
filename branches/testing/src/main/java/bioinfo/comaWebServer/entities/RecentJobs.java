package bioinfo.comaWebServer.entities;

import java.util.ArrayList;
import java.util.List;

public class RecentJobs 
{
	private List<Job> jobs = null;
	
	public RecentJobs()
	{
		jobs = new ArrayList<Job>();
	}

	public List<Job> getJobs() {
		return jobs;
	}
	
	public void addJob(Job job)
	{
		jobs.add(job);
	}
	
}
