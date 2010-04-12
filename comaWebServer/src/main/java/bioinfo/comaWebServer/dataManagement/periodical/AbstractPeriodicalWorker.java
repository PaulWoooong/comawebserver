package bioinfo.comaWebServer.dataManagement.periodical;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.dataManagement.JobStatus;
import bioinfo.comaWebServer.dataManagement.SMTPMailService;
import bioinfo.comaWebServer.dataManagement.transfer.IDataManager;
import bioinfo.comaWebServer.dataManagement.transfer.LocalPBSDataManager;
import bioinfo.comaWebServer.dataManagement.transfer.PublicKeyAuthentication;
import bioinfo.comaWebServer.dataManagement.transfer.RemotePBSDataManager;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.DataFile;
import bioinfo.comaWebServer.entities.EmailNotification;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.exceptions.JobFailureException;


public abstract class AbstractPeriodicalWorker extends TimerTask
{
	private static final Logger periodicalWorkerLog = Logger.getLogger("periodicalWorker");
	
	private boolean run = true;
	
	private long sleep = 10;

	private IDataSource  dataSource  = null;
	private IDataManager dataManager = null;

	public AbstractPeriodicalWorker(IDataSource dataSource)
	{
		this.dataSource 	= dataSource;
	}

	public void run()
	{
		while(run)
		{
			try
			{
				//Status not equals FINISHED nor ERRORS nor CANCELED
				Set<Job> notFinishedJobs = dataSource.getNotFinishedJobs();

				if(notFinishedJobs != null && notFinishedJobs.size() > 0)
				{
					Cluster workstation = Cache.getClusterParams();
					EmailNotification emailNotification = Cache.getEmailNotificationParams();
				
					if(workstation.isLocal())
					{
						dataManager = new LocalPBSDataManager();
					}
					else
					{
						dataManager = new RemotePBSDataManager(PublicKeyAuthentication.connect(workstation.getUsername(), 
								workstation.getHostname(), workstation.getPrivateKeyPath(), workstation.getPassphrase()));
					}
												
					//submits jobs to the workstation
					//if everything is fine sets job status to QUEUED
					
					for(Job job: notFinishedJobs)
					{
						if(job.getStatus().equals(JobStatus.SUBMITTED.getStatus()))
						{
							try
							{
								periodicalWorkerLog.error("Submitting: " + job.getGeneratedId());
								//Before executing query for PBS tests if update is available (maybe DB is shut down)
								dataSource.update(job);
								//Submits job
								String workstationId = jobSubmission(dataManager, workstation.isLocal(), 
																	 workstation.getGlobalFilePath(), 
																	 workstation.getRemoteFilePath(), 
																	 job.getGeneratedId(), 
																	 job.getDataFiles(), job.getType().getType());
								//Sets workstation ID and status to QUEUED
								if(workstationId != null)
								{
									job.setPbsId(workstationId);
									job.setStatus(JobStatus.QUEUED.getStatus());
									dataSource.update(job);	
									periodicalWorkerLog.error("Finished: " + job.getGeneratedId() + " " + job.getStatus());									
								}
							} 
							catch (Exception e) 
							{
								StackTraceElement[] stack = e.getStackTrace();
								for(int i = 0; stack != null && i < stack.length; i++)
								{
									periodicalWorkerLog.error(stack[i].toString());
								}
							}
						}
					}
					
					//retrieves status of the running/waiting/queued jobs
					Map<String, String> jobStatus = dataManager.getJobStatus(workstation.getUsername());

					//tests if a job has already been queued
					//sets new status of not finished jobs
					for(Job job: notFinishedJobs)
					{
						if( job.getStatus() != null && 
							!job.getStatus().equals(JobStatus.SUBMITTED.getStatus()) &&
							!job.getStatus().equals(JobStatus.REGISTERED.getStatus()))
						{
							if(jobStatus.containsKey(job.getPbsId()))
							{
								job.setStatus(jobStatus.get(job.getPbsId()));
							}
							else
							{
								job.setStatus(JobStatus.FINISHED_RUNNING.getStatus());
							}
							
							dataSource.update(job);	
						}
					}
					
					//process finished jobs
					PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
					long expirationPeriod    	= periodicalWorkerParams.getJobExpiration();
					long expirationPeriodErr	= periodicalWorkerParams.getJobWithErrorsExpiration();
 
					for(Job job: notFinishedJobs)
					{
						if(job.getStatus().equals(JobStatus.FINISHED_RUNNING.getStatus()))
						{
							try
							{
								resultsExtraction(dataManager, job, 
												  workstation.getRemoteFilePath(), 
												  workstation.getGlobalFilePath());
								
								job.setExpirationDate(getExpirationDate(expirationPeriod));
								job.setStatus(JobStatus.FINISHED.getStatus());
								dataSource.update(job);
								
								sendEmail(job, workstation.getUrlForResults(), emailNotification.getSender(), emailNotification.getHostname());
							}
							catch (JobFailureException e)
							{
								job.setExpirationDate(getExpirationDate(expirationPeriodErr));
								job.setStatus(JobStatus.ERRORS.getStatus());
								dataSource.update(job);

								sendEmail(job, workstation.getUrlForResults(), emailNotification.getSender(), emailNotification.getHostname());
							}
							catch (Exception e)
							{
								StackTraceElement[] stack = e.getStackTrace();
								for(int i = 0; stack != null && i < stack.length; i++)
								{
									periodicalWorkerLog.error(stack[i].toString());
								}
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					periodicalWorkerLog.error(stack[i].toString());
				}
			}

			try
			{
				Thread.sleep(sleep * 1000);
			}
			catch (InterruptedException e){}
		}
	}
	
	protected abstract void resultsExtraction(IDataManager dataManager, Job job, 
												String remotePath, String localPath) throws Exception;
	
	protected abstract String jobSubmission(IDataManager dataManager, boolean localWorkstation, 
											  String globalFilePath, String remoteFilePath, 
											  String jobGeneratedId, Set<DataFile> dataFiles, String jobType) 
											  throws Exception;

	private static void sendEmail(Job job, String url, String sender, String hostname)
	{
		if(job.getEmail() != null && !job.getEmail().equals(""))
		{
			String subject = null;
			if(job.getDescription() != null && !job.getDescription().equals(""))
			{
				subject = "Finished job: " + job.getDescription();
			}
			else
			{
				subject = "Finished job: " + job.getGeneratedId();
			}
			if(job.getStatus() == JobStatus.ERRORS.getStatus())
			{
				subject += " (with errors)";
			}

			String content = mailContent(job, url);

			try
			{
				SMTPMailService.send(sender, job.getEmail(), content, subject, hostname);
			}
			catch (Exception e)
			{
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					periodicalWorkerLog.error(stack[i].toString());
				}
			}
		}
	}

	private static String mailContent(Job job, String url)
	{
		String info =	"COMA server.\n" +
		"Finished job: " + job.getGeneratedId() + ".\n" +
		"Job expiration date: " + job.getExpirationDate() + ".\n";

		if(job.getDescription() != null)
		{
			info += "Job description: " + job.getDescription() + ".\n";
		}
		
		if(url != null)
		{
			info += "The results can be found here:\n" + url + job.getGeneratedId();
		}
		
		return info;
	}

	private static Date getExpirationDate(long days)
	{
		Date date = new Date();

		date.setTime(date.getTime() + (1000L * 60L) * 60L * 24L * days);

		return date;
	}

	public boolean isRun()
	{
		return run;
	}

	public void setRun(boolean run)
	{
		this.run = run;
	}

}
