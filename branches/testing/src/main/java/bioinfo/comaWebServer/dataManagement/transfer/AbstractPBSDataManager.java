package bioinfo.comaWebServer.dataManagement.transfer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bioinfo.comaWebServer.dataManagement.JobStatus;


public abstract class AbstractPBSDataManager implements IDataManager
{
	                                                        //Job ID        Username Queue  Jobname   SessID  NDS     TSK   Memory    Time      S      Time
	protected Pattern statusPattern = Pattern.compile("^\\s*(\\d+)\\.\\w+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+\\S+.*");
	protected Pattern submissionPattern = Pattern.compile("^\\s*(\\d+)\\.\\w+.*");

/*	E -	 Job is	exiting	after having run.
    H -	 Job is	held.
    Q -	 job is	queued,	eligable to run	or routed.
    R -	 job is	running.
    T -	 job is	being moved to new location.
    W -	 job is	waiting	for its	execution time
	     (-a option) to	be reached.
    S -	 (Unicos only) job is suspend.*/

	
	protected final String RUNNING_STATUS  = "R";
	protected final String FINISHED_STATUS = "C";
	protected final String QUEUED_STATUS   = "Q";
	
	protected String getJobWorkstationId(String line)
	{
		Matcher matcher = statusPattern.matcher(line);
		if(matcher.matches())
		{
			return matcher.group(1);
		}
		return null;
	}

	protected String cancelCommand(String id)
	{
		return "qdel " + id;
	}
	
	protected String statusCommand(String username)
	{
		return "qstat -u " + username;
	}
	
	protected String getJobWorkstationStatus(String line)
	{
		Matcher matcher = statusPattern.matcher(line);
		if(matcher.matches())
		{
			String status = matcher.group(2);

			if(status.equals(QUEUED_STATUS))
			{
				return JobStatus.QUEUED.getStatus();
			}
			if(status.equals(FINISHED_STATUS))
			{
				return JobStatus.FINISHED_RUNNING.getStatus();
			}

			return JobStatus.RUNNING.getStatus();
		}
		return null;
	}
	
	protected String getJobSubmissionId(String line)
	{
		Matcher matcher = submissionPattern.matcher(line);
		if(matcher.matches())
		{
			return matcher.group(1);
		}
		return null;
	}
}
