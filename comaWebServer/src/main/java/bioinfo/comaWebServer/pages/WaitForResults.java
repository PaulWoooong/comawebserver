package bioinfo.comaWebServer.pages;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.annotations.Immutable;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.pages.show.ShowInfo;
import bioinfo.comaWebServer.pages.show.ShowResults;

@IncludeStylesheet("context:assets/styles.css")
public class WaitForResults 
{
	@Inject
	private IDataSource dataSource;
	
	@InjectPage
	private ShowInfo infoPage;
	
	@InjectPage
	private ShowResults showResults;
	
	private String jobId;
	@Immutable
	@Persist("flash")
	private int timeToWait;
	private String status;
	
	public void setUp(String id)
	{
		jobId = id;
		timeToWait = 0;
	}

	String onPassivate()
	{
		return jobId;
	}
	
	Object onActivate()
	{
		if(jobId == null)
		{
			infoPage.setUp("", "A job was not found. It may have been expired. Job ID: " + jobId + "!");
			return infoPage;
		}
		return null;
	}
	
	Object onActivate(String reload)
	{
		try
		{
			jobId = reload;
			
			String stat = dataSource.jobStatus(jobId);

			if(stat.equals(Job.FINISHED) || stat.equals(Job.ERRORS))
			{
				showResults.setUp(jobId);
				
				return showResults;
			}

			status = stat;
			
			timeToWait += 5;
			if(timeToWait > 30)
			{
				timeToWait = 30;
			}
		}
		catch (Exception e)
		{
			infoPage.setUp("", "A job was not found. It may have been expired. Job ID: " + jobId + "!");
			return infoPage;
		}
		
		return null;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(int timeToWait) {
		this.timeToWait = timeToWait;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}