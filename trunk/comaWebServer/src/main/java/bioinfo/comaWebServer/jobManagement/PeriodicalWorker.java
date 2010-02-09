package bioinfo.comaWebServer.jobManagement;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.comparators.ResultsAlignmentComparator;
import bioinfo.comaWebServer.dataServices.IConnection;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.dataServices.IMailService;
import bioinfo.comaWebServer.dataServices.IParser;
import bioinfo.comaWebServer.dataServices.ISSHService;
import bioinfo.comaWebServer.dataServices.ImageProcessor;
import bioinfo.comaWebServer.entities.AlignmentBibliography;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.Image;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.ResultsQuery;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.JobType;
import bioinfo.comaWebServer.exceptions.InitializationException;
import bioinfo.comaWebServer.exceptions.JobFailureException;

public class PeriodicalWorker extends TimerTask
{
	private static final Logger periodicalWorkerLog = Logger.getLogger("periodicalWorker");
	
	private boolean run = true;
	
	private long sleep = 10;

	private IDataSource dataSource 	 = null;
	private IParser parser 			 = null;
	private IMailService mailService 	 = null;
	private ISSHService sshService 	 = null;

	public PeriodicalWorker(IDataSource dataSource, ISSHService sshService,
							  IMailService mailService, IParser parser)
	{
		this.dataSource 	= dataSource;
		this.parser 		= parser;
		this.mailService 	= mailService;
		this.sshService 	= sshService;
	}

	public void run()
	{
		while(run)
		{
			IConnection connection = null;

			try
			{
				connection = sshService.connect();
				//Status not equals FINISHED and ERRORS
				Set<Job> submittedJobs = dataSource.getNotFinishedJobs();

				if(submittedJobs != null)
				{
					Cluster workstation = Cache.getClusterParams();
					PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
					
					if(workstation == null || periodicalWorkerParams == null)
					{
						throw new InitializationException("System has not been initialised yet!");
					}
					
					long expirationPeriod = periodicalWorkerParams.getJobExpiration();
					long expirationPeriodErr = periodicalWorkerParams.getJobWithErrorsExpiration();
					
					for(Job job: submittedJobs)
					{
						if(job.getStatus().equals(Job.REGISTERED))
						{
							
						}
						else if(job.getStatus().equals(Job.CANCELED))
						{
							job.setExpirationDate(getExpirationDate(0));
							dataSource.update(job);
						}
						else if(job.getStatus().equals(Job.SUBMITTED))
						{
							try
							{
								periodicalWorkerLog.error("Submitting: " + job.getGeneratedId());
								submissionJob(job, connection, workstation);
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
						else
						{
							String pbsid = job.getPbsId();
							String status = null;
							
							if(pbsid != null)
							{
								try 
								{
									status = sshService.jobStatus(connection, Cluster.getJobStat(job.getPbsId()));
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
							
							if(status != null && status.equals(Job.FINISHED))
							{
								try
								{
									resultsExtractionJob(job, connection, workstation, expirationPeriod, periodicalWorkerParams);
								}
								catch (JobFailureException e)
								{
									job.setExpirationDate(getExpirationDate(expirationPeriodErr));
									job.setStatus(Job.ERRORS);
									dataSource.update(job);
	
									sendEmail(mailService, job, workstation.getUrlForResults());
	
									sshService.downloadFiles(connection, job.getRemoteFilePath(Extentions.ERR.getExtention()), job.getLocalPath());
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
							else if(status != null)
							{
								job.setStatus(status);
								dataSource.update(job);
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
			finally
			{
				if(connection != null) connection.disconnect();
			}

			try
			{
				Thread.sleep(sleep * 1000);
			}
			catch (InterruptedException e){}

		}
	}
	
	private void resultsExtractionJob(Job job, IConnection connection, 
			Cluster workstation, long expirationPeriod, PeriodicalWorkerParams periodicalWorkerParams) throws Exception
	{
		List<String> files2process = new ArrayList<String>();
		
		if(job.getType() == JobType.COMA_JOB)
		{
			files2process.add(job.getRemoteFilePath(Extentions.OUTPUT_COMA_OUT.getExtention()));
			files2process.add(job.getRemoteFilePath(Extentions.OUTPUT_COMA_ID.getExtention()));
			files2process.add(job.getRemoteFilePath(Extentions.OUTPUT_COMA_MA.getExtention()));
		}
		else if(job.getType() == JobType.MODELLER_JOB)
		{
			files2process.add(job.getRemoteFilePath(Extentions.OUTPUT_MODELLER.getExtention()));
		}
		else if(job.getType() == JobType.MSA_JOB)
		{
			files2process.add(job.getRemoteFilePath(Extentions.OUTPUT_MSA.getExtention()));
		}

		for(String file: files2process)
		{
			sshService.testFile(connection, Cluster.testFileExists(file));
		}

		sshService.downloadFiles(connection, files2process, job.getLocalPath());

		if(job.getType() == JobType.COMA_JOB)
		{
			ComaResults comaResults = job.getComaResults();
			
			parser.parse(comaResults, job.getLocalFilePath(Extentions.OUTPUT_COMA_OUT.getExtention()));
			
			comaResults.setNumberOfSequences(parser.numberOfSeq(job.getLocalFilePath(Extentions.OUTPUT_COMA_MA.getExtention())));
			
			Map<String, AlignmentBibliography> ids = parser.parseIDS(job.getLocalFilePath(Extentions.OUTPUT_COMA_ID.getExtention()));
			setBibliography(comaResults.getAlignments(), ids);
			
			int length = 0;
			
			try 
			{
				length = Integer.parseInt(comaResults.getResultsFooter().getQueryLength());
				
				ImageProcessor imageProcessor = new ImageProcessor(length);
				String localPath = workstation.getTmpLocalFileStoragePath() + job.getGeneratedId() + "/";
				String imgpath = workstation.getTmpLocalFileStoragePathForImg() + job.getGeneratedId() + "/";
				comaResults.setImages(setImages(comaResults.getAlignments(), periodicalWorkerParams,
						imageProcessor, localPath, imgpath));
			} 
			catch (Exception e){}
			
			job.setComaResults(comaResults);
		}

		job.setExpirationDate(getExpirationDate(expirationPeriod));
		job.setStatus(Job.FINISHED);
		dataSource.update(job);

		sendEmail(mailService, job, workstation.getUrlForResults());
	}
	
	private void submissionJob(Job job, IConnection connection, Cluster workstation) throws Exception
	{
		String command 		= null;
		String remotePath 	= job.getRemotePath();
		String generatedId 	= job.getGeneratedId();
		
		File data = null;
		File params = null;
		
		if(job.getType() == JobType.COMA_JOB)
		{
			command = Cluster.getComaCommand(workstation.getCommandComa(), 
											 remotePath, generatedId);
			data   = new File(job.getLocalFilePath(Extentions.INPUT_COMA.getExtention()));
			params = new File(job.getLocalFilePath(Extentions.PARAMS.getExtention()));
		}
		else if(job.getType() == JobType.MODELLER_JOB)
		{
			command = Cluster.getModellerCommand(workstation.getCommandModeller(), 
												 remotePath, generatedId);
			data   = new File(job.getLocalFilePath(Extentions.INPUT_MODELLER.getExtention()));
			params = new File(job.getLocalFilePath(Extentions.PARAMS.getExtention()));
		}
		else if(job.getType() == JobType.MSA_JOB)
		{
			command = Cluster.getMsaCommand(workstation.getCommandMsa(), 
												 remotePath, generatedId);
			data   = new File(job.getLocalFilePath(Extentions.INPUT_MSA_FA.getExtention()));
			params = new File(job.getLocalFilePath(Extentions.INPUT_MSA_COMA.getExtention()));
		}
		
		//Before executing query for PBS tests if update is available (maybe DB is restarting)
		dataSource.update(job);
		
		String pbsId = sshService.submit(connection, data, 
										 params, command, generatedId, remotePath);
		job.setPbsId(pbsId);
		job.setStatus(Job.QUEUED);

		dataSource.update(job);
		
		periodicalWorkerLog.error("Finished: " + job.getGeneratedId() + " " + job.getStatus());
	}
	
	private Set<Image> setImages(Set<ResultsAlignment> alignments, 
			PeriodicalWorkerParams periodicalWorkerParams, ImageProcessor imageProcessor, 
			String localPath, String imgPath) throws IOException
	{
		Set<Image> images = new HashSet<Image>();
		
		ArrayList<ResultsAlignment> sortedAlignments = new ArrayList<ResultsAlignment>(alignments);
		Collections.sort(sortedAlignments, new ResultsAlignmentComparator());

		int counter = 1;
		for(ResultsAlignment alignment: sortedAlignments)
		{
			boolean ok = true;
			double evalue = Double.MAX_VALUE;
			
			try 
			{
				evalue = Double.parseDouble(alignment.getExpect());
			} 
			catch (NumberFormatException e)
			{
				ok = false;
			}
			
			if(ok && evalue 	<= periodicalWorkerParams.getEvalueForPictures() && 
				counter		 	<= periodicalWorkerParams.getNumberForPictures())
			{
				int mainBegin = Integer.MAX_VALUE;
				int mainEnd	   = Integer.MIN_VALUE;
				
				for(ResultsQuery query: alignment.getQueries())
				{
					try 
					{
						int begin  = Integer.parseInt(query.getQueryBegin());
						int end 	= Integer.parseInt(query.getQueryEnd());
						
						if(mainBegin > begin)
						{
							mainBegin = begin;
						}
						if(mainEnd < end)
						{
							mainEnd = end;
						}
					}
					catch (NumberFormatException e) 
					{
						ok = false;
					}
				}
				
				if(ok)
				{
					//0x5 - hexadecimal format
					Color color = Color.BLUE;
					
					if(evalue <= periodicalWorkerParams.getRedThreshold())
					{
						color = Color.RED;
					}
					else if(evalue <= periodicalWorkerParams.getGreenThreshold())
					{
						color = new Color(51, 153, 0x0);
					}
					
					String header = "";
					
					if(alignment.getAlignmentBibliography() != null)
					{
						if(alignment.getAlignmentBibliography().getPdbId() != null)
						{
							header = alignment.getAlignmentBibliography().getPdbId();
							if(alignment.getAlignmentBibliography().getHierarchies() != null)
							{
								header += "(" + alignment.getAlignmentBibliography().getHierarchies() + ")";
							}
						}
					}
					
					imageProcessor.draw(localPath + counter + "." + ImageProcessor.extention, color, header, mainBegin - 1, mainEnd - 1);
					images.add(new Image(imgPath + counter + "." + ImageProcessor.extention, alignment.getPriority()));
					
					counter++;
				}
			} 
		}
		return images;
	}
	
	private void setBibliography(Set<ResultsAlignment> alignments, Map<String, AlignmentBibliography> ids)
	{	
		for(ResultsAlignment alignment: alignments)
		{
			String alName = alignment.getName();

			boolean found = false;
			
			Iterator<Entry<String, AlignmentBibliography>> it = ids.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		        Map.Entry<String, AlignmentBibliography> pairs = (Map.Entry<String, AlignmentBibliography>)it.next();
		        
		        String biblioName = pairs.getKey();

		        if(biblioName.equals(alName))
		        {
		        	alignment.setAlignmentBibliography(pairs.getValue());
		        	found = true;
		        }
		    }
		    
			if(!found)
			{
				periodicalWorkerLog.error("Adding empty bibliography to alignemnt: " + alName + " (id=" + alignment.getId() + ")");
				alignment.setAlignmentBibliography(new AlignmentBibliography());
			}
		}
	}

	private void sendEmail(IMailService mailService, Job job, String url)
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
			if(job.getStatus() == Job.ERRORS)
			{
				subject += " (with errors)";
			}

			String content = mailContent(job, url);

			try
			{
				mailService.send(job.getEmail(), content, subject);
			}
			catch (Exception e){}
		}
	}

	private String mailContent(Job job, String url)
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

	private Date getExpirationDate(long days)
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
