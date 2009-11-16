package bioinfo.comaWebServer.jobManagement;

import java.io.File;
import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import bioinfo.comaWebServer.dataServices.IConnection;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.dataServices.ISSHService;
import bioinfo.comaWebServer.entities.Job;

public class PeriodicalGarbageCollector  extends TimerTask 
{
	private static final Logger periodicalGarbageCollectorLog = 
								Logger.getLogger("periodicalGarbageCollector");
	
	private boolean run = true;

	private IDataSource dataSource;
	private long period = 1;
	private ISSHService sshService = null;
	
	public PeriodicalGarbageCollector(IDataSource dataSource, ISSHService sshService)
	{
		this.dataSource = dataSource;
		this.sshService = sshService;
	}
	public void run() 
	{
		while(run)
		{
			Set<Job> expiredJobs 	= null;
			IConnection connection 	= null;
			try 
			{
				connection = sshService.connect();
				
				expiredJobs = dataSource.getExpiredJobs();

				for(Job job: expiredJobs)
				{
					try 
					{
						sshService.deleteDir(connection, job.getRemotePath());

						FileUtils.forceDelete(new File(job.getLocalPath()));

						dataSource.delete(job);
					} 
					catch (Exception e) 
					{
						System.err.println(e.getMessage());
					}
				}
			} 
			catch (Exception e)
			{
				StackTraceElement[] stack = e.getStackTrace();
				
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					periodicalGarbageCollectorLog.error(stack[i].toString());	
				}
			}
			finally
			{
				if(connection != null) connection.disconnect();
			}
			
			try 
			{
				Thread.sleep(1000L * 60L * 60L * 24L * period);
			} 
			catch (InterruptedException e){}
		}
		
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
