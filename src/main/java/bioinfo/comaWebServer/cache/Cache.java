package bioinfo.comaWebServer.cache;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.dataServices.HibernateDataSource;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.EmailNotification;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;

public class Cache 
{
	private static Cluster clusterParams;
	private static EmailNotification emailNotificationParams;
	private static PeriodicalWorkerParams periodicalWorkerParams;
	private static IDataSource dataSource = new HibernateDataSource();
	
	public static synchronized void refreshPeriodicalWorkerParams()
	{
		periodicalWorkerParams = null;
	}
	
	public static synchronized PeriodicalWorkerParams getPeriodicalWorkerParams()
	{
		if(periodicalWorkerParams == null)
		{
			periodicalWorkerParams = dataSource.getPeriodicalWorkerParams();
		}
		return periodicalWorkerParams;
	}
	
	public static synchronized void refreshClusterParams()
	{
		clusterParams = null;
	}
	
	public static synchronized Cluster getClusterParams()
	{
		if(clusterParams == null)
		{
			clusterParams = dataSource.getClusterParams();
		}
		return clusterParams;
	}
	
	public static synchronized void refreshEmailNotificationParams()
	{
		emailNotificationParams = null;
	}
	
	public static synchronized EmailNotification getEmailNotificationParams()
	{
		if(emailNotificationParams == null)
		{
			emailNotificationParams = dataSource.getEmailNotificationParams();
		}
		return emailNotificationParams;
	}
}
