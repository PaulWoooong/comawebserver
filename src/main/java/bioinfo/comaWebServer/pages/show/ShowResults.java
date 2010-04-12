package bioinfo.comaWebServer.pages.show;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import bioinfo.comaWebServer.comparators.ResultsHitComparator;
import bioinfo.comaWebServer.dataManagement.JobStatus;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.entities.ResultsHit;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.JobType;
import bioinfo.comaWebServer.jobManagement.JobSubmitter;
import bioinfo.comaWebServer.pages.WaitForResults;
import bioinfo.comaWebServer.util.CookieManager;

@IncludeStylesheet("context:assets/styles.css")
public class ShowResults
{
	@InjectPage
	private WaitForResults waitForResults;
	@Inject
	private IDataSource dataSource;
	
	private Job job;
	private String jobId;
	private List<String> errNotes;
	private String errNote;
	
	void onException(Throwable cause)
    {
		cause.printStackTrace();
		submitMSAForm.recordError(cause.getMessage());
    }

	public void setUp(String id)
	{
		jobId = id;
	}

	String onPassivate()
	{
		return String.valueOf(jobId);
	}
	
	void onActivate()
	{
		if(jobId == null) jobId = "0";
	}

	Object onActivate(String id)
	{
		jobId = id;
		
		if(jobId == null) jobId = "";
		
		try
		{
			job = dataSource.getJobByGeneratedIdORDescription(jobId);

			if(!job.getStatus().equals(JobStatus.FINISHED.getStatus()) && !job.getStatus().equals(JobStatus.ERRORS.getStatus()))
			{
				waitForResults.setUp(jobId);
				
				return waitForResults;
			}
			
			if(job.getType() == JobType.COMA_JOB)
			{
				if (getSelectedHits() == null)
		        {  
		            setSelectedHits(new HashSet<Integer>());  
		        }  
		        if(getHits() == null)
				{
					ArrayList<ResultsHit> sortedList = new ArrayList<ResultsHit>(job.getComaResults().getHits());
					Collections.sort(sortedList, new ResultsHitComparator());
					setHits(sortedList);
				}	
			}
		}
		catch (Exception e)
		{
			submitMSAForm.recordError("A job was not found. It may have been expired. Job ID: " + jobId + "!");
			e.printStackTrace();
		}
		return null;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public boolean isComaJob()
	{
		if(job == null || job.getType() != JobType.COMA_JOB)
		{
			return false;
		}

		return true;
	}
	
	public boolean isModellerJob()
	{
		if(job == null || job.getType() != JobType.MODELLER_JOB)
		{
			return false;
		}

		return true;
	}
	
	public boolean isMsaJob()
	{
		if(job == null || job.getType() != JobType.MSA_JOB)
		{
			return false;
		}

		return true;
	}

	public List<String> getErrNotes()
	{
		if(job == null || !job.getStatus().equals(JobStatus.ERRORS.getStatus()))
		{
			return null;
		}
		
		errNotes = new ArrayList<String>();
		
		DataInputStream in 		= null;
		FileInputStream fstream = null;
		
		try
		{
			File errFile = new File(job.getLocalFilePath(Extentions.ERR.getExtention()));
			fstream = new FileInputStream(errFile);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			while ((strLine = br.readLine()) != null)
			{
				errNotes.add(strLine);
			}
		}
		catch (IOException e){}
		finally
		{
			try 
			{
				if(in != null) in.close();
				if(fstream != null) fstream.close();
			} 
			catch (IOException e) {}
		}

		if(errNotes.size() == 0)
		{
			errNotes.add("Errors have occured when processing the job.");
			errNotes.add("Notification has been emailed to webmaster.");
		}

		return errNotes;
	}

	public String getErrNote() {
		return errNote;
	}

	public void setErrNote(String errNote) {
		this.errNote = errNote;
	}
	
	//Check boxes
	//==========================================================================
	@ApplicationState
	private RecentJobs recentJobs;
	private boolean recentJobsExists;
	
	@Inject
	private Cookies cookies;
	
	@Component
	private Form submitMSAForm;
	
	public Object onSuccess()
    {  
		if(selectedHits == null || selectedHits.size() == 0)
		{
			submitMSAForm.recordError("No hits were selected!");
			return null;
		}
		
		JobSubmitter jobSubmitter = new JobSubmitter();
		
		String generatedId;
		try 
		{
			generatedId = jobSubmitter.submitMsaJob(selectedHits, dataSource, job.getGeneratedId());
			recentJobs.addJob(dataSource.getJobByGeneratedId(generatedId));
			CookieManager.registerJob(generatedId, cookies);
		} 
		catch (Exception e) 
		{
			submitMSAForm.recordError(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		waitForResults.setUp(generatedId);
		
		selectedHits = null;
		
		return waitForResults;
    }
	
	
	private List<ResultsHit> hits;
	@Persist
	private Set<Integer> selectedHits;
	private ResultsHit resultsHit;
	
	public ResultsHit getResultsHit() {
		return resultsHit;
	}

	public void setResultsHit(ResultsHit resultsHit) {
		this.resultsHit = resultsHit;
	}
	
	public List<ResultsHit> getHits() {
		return hits;
	}

	public void setHits(List<ResultsHit> hits) {
		this.hits = hits;
	}

	public Set<Integer> getSelectedHits() {
		return selectedHits;
	}

	public void setSelectedHits(Set<Integer> selectedHits) {
		this.selectedHits = selectedHits;
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
