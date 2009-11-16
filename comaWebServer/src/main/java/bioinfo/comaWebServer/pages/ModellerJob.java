package bioinfo.comaWebServer.pages;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.Input;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.jobManagement.JobSubmitter;
import bioinfo.comaWebServer.pages.show.ShowInfo;

public class ModellerJob 
{
	private static final Logger modellerJobLog = Logger.getLogger("modellerJob");
	
	@ApplicationState
	private RecentJobs recentJobs;
	private boolean recentJobsExists;
	
	@Inject
	private IDataSource dataSource;

	@Persist
	private String description;
	@Persist
	private String email;
	@Persist
	private ResultsAlignment resultsAlignment;
	@Persist
	private String usedDB;
	
	public void setValues(long alignmentId) throws Exception
	{
		try 
		{
			resultsAlignment = dataSource.getResultsAlignment(alignmentId);
			long comaResultsId = resultsAlignment.getComaResultsId();
			
			modellerJobLog.error("Setting values: comaResultsId=" + comaResultsId + " alignmentId=" + alignmentId);

			ComaResults comaResults = dataSource.getComaResultsById(comaResultsId);
			
			usedDB = comaResults.getProfileDBValue();
		} 
		catch (Exception e) 
		{
			throw new Exception();
		}
		if(resultsAlignment == null)
		{
			throw new Exception();
		}
	}
	
	@InjectPage
	private ShowInfo infoPage;
	@InjectPage
	private WaitForResults waitForResults;
	
	Object onSuccess()
	{	
		String id = "";
		try
		{
			JobSubmitter jobSubmitter = new JobSubmitter();
			Input input = new Input();
			input.setDescription(description);
			input.setEmail(email);
			id = jobSubmitter.submitModellerJob(input, 
											    dataSource,
											    usedDB,
											    resultsAlignment);
		}
		catch (IOException e) 
		{
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				modellerJobLog.error(stack[i].toString());
			}
			
			infoPage.setUp("", "IOException: system is unavailable for the moment!");
			return infoPage;
		} 
		catch (Exception e) 
		{
			infoPage.setUp("", e.getMessage());
			return infoPage;
		}
		
		recentJobs.addJob(id);
		
		waitForResults.setUp(id);
		
		return waitForResults;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ResultsAlignment getResultsAlignment() {
		return resultsAlignment;
	}

	public void setResultsAlignment(ResultsAlignment resultsAlignment) {
		this.resultsAlignment = resultsAlignment;
	}
	
	Object onException(Throwable cause)
    {

    	infoPage.setUp("", "We are sorry but the service is temporary unavailable.");

        return infoPage;
    }
	public RecentJobs getRecentJobs() {
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
