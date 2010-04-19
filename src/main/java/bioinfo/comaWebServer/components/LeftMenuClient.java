package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.pages.WaitForResults;
import bioinfo.comaWebServer.util.CookieManager;


@IncludeStylesheet("context:assets/styles.css")
public class LeftMenuClient 
{	
	@Persist
	private String jobId;
	@InjectPage
	private WaitForResults results;
	
	Object onSuccess()
	{
		if(jobId != null)
		{
			results.setUp(jobId);
			
			jobId = null;
			
			return results;
		}

		return this;
	}
	
	@InjectPage
	private WaitForResults waitForResults;
	
	Object onActionFromShowJob(String id) throws Exception
    {
		waitForResults.setUp(id);
        
        return waitForResults;
    }

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	private Job job;
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@ApplicationState
	private RecentJobs recentJobs;
	private boolean recentJobsExists;
	
	@Inject
	private IDataSource dataSource;
	@Inject
	private Cookies cookies;

	public RecentJobs getRecentJobs() 
	{
		if(!recentJobsExists)
		{
			List<Job> jobs = CookieManager.getJobs(dataSource, cookies);
			for(Job job: jobs)
			{
				recentJobs.addJob(job);
			}
		}
		return recentJobs;
	}

	public void setRecentJobs(RecentJobs recentJobs) {
		this.recentJobs = recentJobs;
	}

	public boolean isRecentJobsExists() {
		return recentJobsExists;
	}

	public void setRecentJobsExists(boolean recentJobsExists) {
		this.recentJobsExists = recentJobsExists;
	}


}
