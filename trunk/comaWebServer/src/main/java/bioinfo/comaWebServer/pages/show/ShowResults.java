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
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.comparators.ResultsHitComparator;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.entities.ResultsHit;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.JobType;
import bioinfo.comaWebServer.jobManagement.JobSubmitter;
import bioinfo.comaWebServer.pages.WaitForResults;

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
	
	@InjectPage
	private ShowInfo infoPage;
	
	Object onException(Throwable cause)
    {
    	infoPage.setUp("", "We are sorry but there was a fatal error when handling query.");

        return infoPage;
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
			job = dataSource.getJobByGeneratedId(jobId);

			if(!job.getStatus().equals(Job.FINISHED) && !job.getStatus().equals(Job.ERRORS))
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
		if(job == null || !job.getStatus().equals(Job.ERRORS))
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
	
	public Object onSuccess() throws Exception 
    {  
		if(selectedHits == null || selectedHits.size() == 0) return null;
		
		JobSubmitter jobSubmitter = new JobSubmitter();
		
		String generatedId = jobSubmitter.submitMsaJob(selectedHits, dataSource, job.getGeneratedId());
		
		waitForResults.setUp(generatedId);
		
		selectedHits = null;
		recentJobs.addJob(dataSource.getJobByGeneratedId(generatedId));
		
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