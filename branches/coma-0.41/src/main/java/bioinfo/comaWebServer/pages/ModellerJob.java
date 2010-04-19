package bioinfo.comaWebServer.pages;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.Input;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.jobManagement.JobSubmitter;
import bioinfo.comaWebServer.util.CookieManager;

public class ModellerJob 
{
	private static final Logger modellerJobLog = Logger.getLogger("modellerJob");
	
	@ApplicationState
	private RecentJobs recentJobs;
	private boolean recentJobsExists;
	
	@Component
	private Form submitModellerJobForm;
	
	@Inject
	private IDataSource dataSource;

	@Persist
	private String description;
	@Persist
	private String email;
	@Persist
	private String key;
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
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				modellerJobLog.error(stack[i].toString());
			}
			
			submitModellerJobForm.recordError(e.getMessage());
		}
	}

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
											    resultsAlignment,
											    key);
			
			recentJobs.addJob(dataSource.getJobByGeneratedId(id));
			CookieManager.registerJob(id, cookies);
		}
		catch (Exception e) 
		{
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				modellerJobLog.error(stack[i].toString());
			}
			
			submitModellerJobForm.recordError(e.getMessage());
			return null;
		}
		
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
	
	void onException(Throwable cause)
    {
		cause.printStackTrace();
		submitModellerJobForm.recordError(cause.getMessage());
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Inject
	private Cookies cookies;
}
