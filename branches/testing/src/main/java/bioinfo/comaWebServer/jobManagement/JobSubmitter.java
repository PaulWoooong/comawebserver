package bioinfo.comaWebServer.jobManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tapestry5.upload.services.UploadedFile;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.comparators.ResultsAlignmentComparator;
import bioinfo.comaWebServer.dataManagement.JobStatus;
import bioinfo.comaWebServer.dataManagement.PasswordManager;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.AbstractParameter;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.DataFile;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.EmailNotification;
import bioinfo.comaWebServer.entities.Input;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.InputType;
import bioinfo.comaWebServer.enums.JobType;
import bioinfo.comaWebServer.exceptions.InitializationException;
import bioinfo.comaWebServer.exceptions.JobNotFoundException;

public class JobSubmitter
{
	private static final Logger jobSubmitterLog = Logger.getLogger("jobSubmitter");
	
	private static final int MAX_RUNNING_JOBS = 1000;
	
	public String submitComaJob(Input input, 
								 List<AbstractParameter> params,
								 IDataSource dataSource, 
								 Search search, 
								 DatabaseItem pro) 
						  			throws  Exception
	{
		Cluster cluster = Cache.getClusterParams();
		String localDataPath = cluster.getGlobalFilePath();
		
		Job job = null;
		
		try 
		{
			job = registerJob(dataSource, input.getDescription(), input.getEmail(), MAX_RUNNING_JOBS);
			
			commonTasks(dataSource, job, localDataPath, cluster.getRemoteFilePath());
			
			/*
			 *Job specific task 
			 */
			String dataFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.INPUT_COMA.getExtention());
			getDataFile(input.getFile(), input.getSequence(), dataFileName, input.getType());
			
			String paramsFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.PARAMS.getExtention());
			getParamsFile(params, paramsFileName);
			
			ComaResults comaResults = new ComaResults();
			comaResults.setProfileDBName(pro.getName());
			comaResults.setProfileDBValue(pro.getValue());
			comaResults.setSupportModeling(!pro.isNoModeling());
			
			if(search.getDatabaseItem() != null)
			{
				comaResults.setSequenceDBName(search.getDatabaseItem().getName());
			}
			
			if(search.getDatabaseItem() != null)
			{
				comaResults.setSequenceDBValue(search.getDatabaseItem().getValue());
			}
			comaResults.setSearch(new Search(search));
			
			job.setComaResults(comaResults);
			
			/*
			 * File handling
			 */ 
			//files to send to workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.INPUT_COMA.getExtention(), DataFile.INPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.PARAMS.getExtention(), DataFile.INPUT));
			//files to download from workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.OUTPUT_COMA_OUT.getExtention(), DataFile.OUTPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.OUTPUT_COMA_ID.getExtention(), DataFile.OUTPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.OUTPUT_COMA_MA.getExtention(), DataFile.OUTPUT));
			
			/*
			 * Changing status
			 */
			job.setType(JobType.COMA_JOB);
			job.setStatus(JobStatus.SUBMITTED.getStatus());

			dataSource.update(job);
		} 
		catch (Exception e) 
		{
			if(job != null)
			{
				PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
				
				job.setStatus(JobStatus.ERRORS.getStatus());
				job.setLocalPath(localDataPath + job.getGeneratedId() + File.separator);
				job.setExpirationDate(getExpirationDate(periodicalWorkerParams.getJobWithErrorsExpiration()));
				dataSource.update(job);	
			}
			
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				jobSubmitterLog.error(stack[i].toString());
			}

			throw e;
		}
		
		return job.getGeneratedId();
	}
	
	public String submitModellerJob(Input input, 
									 IDataSource dataSource, 
									 String usedDB,
									 ResultsAlignment alignment,
									 String key) throws  Exception
	{
		Cluster cluster = Cache.getClusterParams();
		String localDataPath = cluster.getGlobalFilePath();
		
		Job job = null;
		
		try 
		{
			job = registerJob(dataSource, input.getDescription(), input.getEmail(), MAX_RUNNING_JOBS);
			
			commonTasks(dataSource, job, localDataPath, cluster.getRemoteFilePath());

			/*
			 *Job specific task 
			 */
			String dataFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.INPUT_MODELLER.getExtention());
			alignment2file(dataFileName, alignment);
			
			String paramsFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.PARAMS.getExtention());
			databaseItem2params(paramsFileName, usedDB);
			
			String keyFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.KEY_MODELLER.getExtention());
			data2file(keyFileName, key);

			/*
			 * File handling
			 */ 
			//files to send to workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.INPUT_MODELLER.getExtention(), DataFile.INPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.PARAMS.getExtention(), DataFile.INPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.KEY_MODELLER.getExtention(), DataFile.INPUT));
			//files to download from workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.OUTPUT_MODELLER.getExtention(), DataFile.OUTPUT));
			
			/*
			 * Changing status
			 */
			job.setType(JobType.MODELLER_JOB);
			job.setStatus(JobStatus.SUBMITTED.getStatus());

			dataSource.update(job);
		} 
		catch (Exception e) 
		{
			if(job != null)
			{
				PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
				
				job.setStatus(JobStatus.ERRORS.getStatus());
				job.setLocalPath(localDataPath + job.getGeneratedId() + File.separator);
				job.setExpirationDate(getExpirationDate(periodicalWorkerParams.getJobWithErrorsExpiration()));
				dataSource.update(job);	
			}
			
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				jobSubmitterLog.error(stack[i].toString());
			}
			
			throw e;
		}
	
		return job.getGeneratedId();
	}
	
	public String submitMsaJob(Set<Integer> priorities, 
			 					IDataSource dataSource, 
								String fatherGeneratedId) throws  Exception
	{
		final Job fatherJob = dataSource.getJobByGeneratedId(fatherGeneratedId);
		if(fatherJob == null) throw new JobNotFoundException(fatherGeneratedId);
		
		Cluster cluster = Cache.getClusterParams();
		String localDataPath = cluster.getGlobalFilePath();
		
		Job job = null;
		
		try 
		{
			job = registerJob(dataSource, null, null, MAX_RUNNING_JOBS);
			
			commonTasks(dataSource, job, localDataPath, cluster.getRemoteFilePath());
			
			/*
			 *Job specific task 
			 */
			String dataFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.INPUT_MSA_FA.getExtention());
			String inputFileName = fatherJob.getLocalFilePath(Extentions.INPUT_COMA.getExtention());
			copyfile(inputFileName, dataFileName);
			
			String paramsFileName = localFilePath(localDataPath, job.getGeneratedId(), Extentions.INPUT_MSA_COMA.getExtention()); 

			List<ResultsAlignment> wantedAlignments = new ArrayList<ResultsAlignment>();
			for(ResultsAlignment alignment: fatherJob.getComaResults().getAlignments())
			{
				if(priorities.contains(alignment.getPriority()))
				{
					wantedAlignments.add(alignment);
				}
			}
			Collections.sort(wantedAlignments, new ResultsAlignmentComparator());
			alignments2file(paramsFileName, wantedAlignments);
			
			/*
			 * File handling
			 */ 
			//files to send to workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.INPUT_MSA_FA.getExtention(), DataFile.INPUT));
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.INPUT_MSA_COMA.getExtention(), DataFile.INPUT));
			//files to download from workstation
			job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.OUTPUT_MSA.getExtention(), DataFile.OUTPUT));
			
			/*
			 * Changing status
			 */
			job.setType(JobType.MSA_JOB);
			job.setStatus(JobStatus.SUBMITTED.getStatus());
			
			dataSource.update(job);
		} 
		catch (Exception e) 
		{
			if(job != null)
			{
				PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
				
				job.setStatus(JobStatus.ERRORS.getStatus());
				job.setLocalPath(localDataPath + job.getGeneratedId() + File.separator);
				job.setExpirationDate(getExpirationDate(periodicalWorkerParams.getJobWithErrorsExpiration()));
				dataSource.update(job);	
			}
		
			StackTraceElement[] stack = e.getStackTrace();
			for(int i = 0; stack != null && i < stack.length; i++)
			{
				jobSubmitterLog.error(stack[i].toString());
			}
			
			throw e;
		}
	
		return job.getGeneratedId();
	}
	
	private String localFilePath(String localPath, String generatedid, String ext)
	{
		return localPath + generatedid + File.separator + generatedid + ext;
	}
	
	private void copyfile(String srFile, String dtFile) throws IOException
	{
	      File f1 = new File(srFile);
	      File f2 = new File(dtFile);
	      InputStream in = new FileInputStream(f1);

	      OutputStream out = new FileOutputStream(f2);

	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0)
	      {
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();
	}

	private void databaseItem2params(String fileName, String value) throws IOException
	{
		File file = new File(fileName);
		FileWriter fstream = null;
		BufferedWriter out = null;
		
		try 
		{
			fstream = new FileWriter(file);
			out = new BufferedWriter(fstream);
			
			out.write("d=" + value);
		} 
		catch (IOException e) 
		{
			throw e;
		}
		finally
		{
			if(out != null) out.close();
			if(fstream != null) fstream.close();
		}

	}
	
	private void alignments2file(String fileName, List<ResultsAlignment> alignments) throws IOException
	{		
		FileWriter fstreamOut = null;
		BufferedWriter out = null;
		try 
		{
			File outFile = new File(fileName);
			
			fstreamOut = new FileWriter(outFile);
			out = new BufferedWriter(fstreamOut);
			
			for(ResultsAlignment alignment: alignments)
			{
				out.write(alignment.toString());
				out.write("\n");
			}
		} 
		catch (IOException e) 
		{
			throw e;
		}
		finally
		{
			if(out != null) out.close();
			if(fstreamOut != null) fstreamOut.close();
		}
	}
	
	private void alignment2file(String fileName, ResultsAlignment alignment) throws IOException
	{		
		FileWriter fstreamOut = null;
		BufferedWriter out = null;
		try 
		{
			File outFile = new File(fileName);
			
			fstreamOut = new FileWriter(outFile);
			out = new BufferedWriter(fstreamOut);
			
			out.write(alignment.toString());
		} 
		catch (IOException e) 
		{
			throw e;
		}
		finally
		{
			if(out != null) out.close();
			if(fstreamOut != null) fstreamOut.close();
		}
	}
	
	private void data2file(String fileName, String data) throws IOException
	{		
		FileWriter fstreamOut = null;
		BufferedWriter out = null;
		try 
		{
			File outFile = new File(fileName);
			
			fstreamOut = new FileWriter(outFile);
			out = new BufferedWriter(fstreamOut);
			
			out.write(data);
		} 
		catch (IOException e) 
		{
			throw e;
		}
		finally
		{
			if(out != null) out.close();
			if(fstreamOut != null) fstreamOut.close();
		}
	}
	
	private void getParamsFile(List<AbstractParameter> params, String fileName) 
															throws IOException
	{
		File file = new File(fileName);
		
		FileWriter fstream = null;
		BufferedWriter out = null;
		try 
		{
			fstream = new FileWriter(file);
			out = new BufferedWriter(fstream);
			
			out.write("## COMA/CONDOR options\n");
			out.write("##\n\n");
			out.write("[OPTIONS]\n\n");
			
			for(AbstractParameter param: params)
			{
				out.write(param.getValues());
			}
		} 
		catch (IOException e) 
		{
			throw e;
		}
		finally
		{
			if(out != null) out.close();
			if(fstream != null) fstream.close();	
		}
	}
	
	private void getDataFile(UploadedFile uploadedFile, String sequence, 
							 				String fileName, InputType format) throws Exception
	{
		File f = null;
		FileOutputStream fop = null;

		try 
		{
			if(uploadedFile != null)
			{
				f = new File(fileName);
				uploadedFile.write(f);
			}
			else
			{
				f = new File(fileName);
				fop = new FileOutputStream(f);
				fop.write(sequence.getBytes());
				fop.flush();
			}
		} 
		catch (Exception e) 
		{
			throw e;
		}
		finally
		{
			if(fop != null) fop.close();
		}
	}
	
	private Date getExpirationDate(long days)
	{
		Date date = new Date();

		date.setTime(date.getTime() + (1000L * 60L) * 60L * 24L * days);

		return date;
	}
	
	private static synchronized Job registerJob(	IDataSource dataSource, 
													String description, 
													String email,
													int maxSubmittedJobs) throws  Exception
	{
		Cluster cluster = Cache.getClusterParams();
		if(cluster == null) throw new InitializationException("The system has not been initialized yet: workstation params!");
		
		EmailNotification emailNotification = Cache.getEmailNotificationParams();
		if(emailNotification == null) throw new InitializationException("The system has not been initialized yet: mail service params!");
		
		long activeJobs = dataSource.runningJobsNumber();
		if(maxSubmittedJobs <= activeJobs)
		{
			throw new Exception("There are too many submitted jobs: " + activeJobs + "!");
		}
		
		Job job = new Job();
		
		job.setDescription(description);
		job.setEmail(email);
		
		dataSource.save(job);
		
		return job;
	}

	private static synchronized void makeLocalDir(String path) throws Exception
	{
		File file = new File(path);
		
		if(!file.exists())
		{
			boolean success = file.mkdirs();
		
			if(!success)
			{
				throw new Exception("ERR: failed creating directory: " + path);
			}
		}
		else
		{
			throw new Exception("ERR: the directory was created before: " + path);
		}
	}

	private static synchronized void commonTasks(IDataSource dataSource, Job job, 
							String globalFilePath, String remoteFilePath) throws Exception
	{
	
		String generatedId = PasswordManager.encrypt(String.valueOf(job.getId()));
		job.setGeneratedId(generatedId);
		
		job.setLocalPath(globalFilePath + generatedId + File.separator);
		job.setRemotePath(remoteFilePath + generatedId + "/");
		
		//logs
		job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.ERR.getExtention(), DataFile.OUTPUT));
		job.getDataFiles().add(new DataFile(job.getGeneratedId() + Extentions.LOG.getExtention(), DataFile.OUTPUT));
		
		job.setStatus(JobStatus.REGISTERED.getStatus());
		
		dataSource.update(job);
		
		makeLocalDir(globalFilePath + generatedId);
	}
}
