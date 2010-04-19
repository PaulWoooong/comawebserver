package bioinfo.comaWebServer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.services.Cookies;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;


public class CookieManager 
{
	private static final int expirationPeriod = 60 * 60 * 48;
	private static final int maxIDexpirationPeriod = expirationPeriod + 1;
	
	private static final String MAX_ID = "max_ID_expiration_period";

	public static List<Job> getJobs(IDataSource dataSource, Cookies cookies)
	{
		List<Job> jobs = new ArrayList<Job>();
		
		long maxID = getMaxID(cookies);
		
		for(long i = 0; i <= maxID; i++)
		{
			String id = cookies.readCookieValue(getComposedJobID(i));
			if(id != null)
			{
				try
				{
					Job job = dataSource.getJobByGeneratedId(id);
					if(job != null)
					{
						jobs.add(job);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return jobs;	
	}
	
	public static void registerJob(String id, Cookies cookies)
	{
		long vacantID = getVacantID(cookies);
		
		cookies.writeCookieValue(getComposedJobID(vacantID), id, expirationPeriod);
		
		saveMaxID(cookies, vacantID);
	}
	
	private static String getComposedJobID(long id)
	{
		return "periodical_job_ID" + id;
	}
	
	private static void saveMaxID(Cookies cookies, long maxID)
	{
		cookies.writeCookieValue(MAX_ID, maxID + "", maxIDexpirationPeriod);
	}
	
	private static long getVacantID(Cookies cookies)
	{
		return getMaxID(cookies) + 1;
	}
	
	private static long getMaxID(Cookies cookies)
	{
		String maxID = cookies.readCookieValue(MAX_ID);
		if(maxID != null)
		{
			return Long.parseLong(maxID);
		}
		return 0;
	}

}
