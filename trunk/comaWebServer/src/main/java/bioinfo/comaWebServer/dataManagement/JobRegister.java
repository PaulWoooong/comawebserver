package bioinfo.comaWebServer.dataManagement;

import java.io.File;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;

public class JobRegister
{
	private JobRegister(){}
	
	public static synchronized Job registerJob(	IDataSource dataSource, 
													String description, 
													String email,
													int maxSubmittedJobs,
													String globalFilePath) throws  Exception
	{
		long activeJobs = dataSource.runningJobsNumber();
		if(maxSubmittedJobs <= activeJobs)
		{
			throw new Exception("There are too many submitted jobs: " + activeJobs + "!");
		}

		Job job = new Job();
			
		job.setDescription(description);
		job.setEmail(email);
	
		dataSource.save(job);
	
		String generatedId = PasswordManager.encrypt(String.valueOf(job.getId()));
		job.setGeneratedId(generatedId);
	
		makeLocalDir(globalFilePath + generatedId);
	
		dataSource.update(job);

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
}
