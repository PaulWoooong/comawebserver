package bioinfo.comaWebServer.services;

import java.util.Timer;

import javax.servlet.ServletException;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;

import bioinfo.comaWebServer.dataManagement.periodical.PeriodicalGarbageCollector;
import bioinfo.comaWebServer.dataManagement.transfer.PublicKeyAuthentication;
import bioinfo.comaWebServer.dataServices.HibernateDataSource;
import bioinfo.comaWebServer.dataServices.IDataSource;

import bioinfo.comaWebServer.jobManagement.PeriodicalCOMAWorker;
import bioinfo.comaWebServer.jobManagement.PeriodicalDatabaseUpdater;

public class ComaWebServerFilter extends TapestryFilter
{
	private IDataSource dataSource = null;
	
	private Timer timerJob 							= null;
	private Timer timerCollector 				   	= null;
	private Timer timerUpdater 				   		= null;
	private PeriodicalCOMAWorker periodicalJob 	 		= null;
	private PeriodicalDatabaseUpdater	updater		= null;
	private PeriodicalGarbageCollector collector 	= null;

	public ComaWebServerFilter()
	{
		dataSource 		 = new HibernateDataSource();

		timerJob 		 = new Timer();
		timerCollector   = new Timer();
		timerUpdater	 = new Timer();
		
		periodicalJob    = new PeriodicalCOMAWorker(dataSource);
		collector 		 = new PeriodicalGarbageCollector(dataSource);
		updater			 = new PeriodicalDatabaseUpdater(dataSource);
	}
	
	protected void init(Registry registry) throws ServletException
	{
		super.init(registry);
		
		dataSource.initializeSystem();
		
		timerJob.schedule(periodicalJob, 0);
		timerCollector.schedule(collector, 0);
		timerUpdater.schedule(updater, 0);
	}
	
	protected void destroy(Registry registry)
	{
		periodicalJob.setRun(false);
		collector.setRun(false);
		updater.setRun(false);
		
		timerJob.cancel();
		timerCollector.cancel();
		timerUpdater.cancel();
		
		PublicKeyAuthentication.disconnect();
		
		super.destroy(registry);
	}
}
