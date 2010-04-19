package bioinfo.comaWebServer.jobManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.dataManagement.transfer.IDataManager;
import bioinfo.comaWebServer.dataManagement.transfer.LocalPBSDataManager;
import bioinfo.comaWebServer.dataManagement.transfer.PublicKeyAuthentication;
import bioinfo.comaWebServer.dataManagement.transfer.RemotePBSDataManager;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.DatabaseItem;

public class PeriodicalDatabaseUpdater extends TimerTask
{
	private static final Logger periodicalDatabaseUpdaterLog = Logger.getLogger("periodicalDatabaseUpdater");

	private boolean run = true;

	private IDataSource dataSource;
	private long period = 1;

	public PeriodicalDatabaseUpdater(IDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	public void run() 
	{
		while(run)
		{
			try 
			{
				Cluster workstation = Cache.getClusterParams();
				
				IDataManager dataManager = null;
				
				if(workstation.isLocal())
				{
					dataManager = new LocalPBSDataManager();
				}
				else
				{
					dataManager = new RemotePBSDataManager(PublicKeyAuthentication.connect(workstation.getUsername(), 
							workstation.getHostname(), workstation.getPrivateKeyPath(), workstation.getPassphrase()));
				}

				List<String> types = new ArrayList<String>();
				types.add(DatabaseItem.SEQUENCE_DB);
				types.add(DatabaseItem.PROFILE_DB);
				
				for(String type: types)
				{
					List<DatabaseItem> dbs = dataSource.getDatabases(type);
					
					for(DatabaseItem item: dbs)
					{
						if(item.getValue() != null && item.getPath() != null)
						{
							try 
							{
								String date = dataManager.fileLastModified(item.getPath());
								if(item.getPartialName() != null)
								{
									item.setName(item.getPartialName() + "_" + date);
								}
								else
								{
									item.setName(item.getValue() + "_" + date);
								}
								
								dataSource.saveOrUpdate(item);
							}
							catch (Exception e) 
							{
								periodicalDatabaseUpdaterLog.error(e.getMessage());
								StackTraceElement[] stack = e.getStackTrace();
								for(int i = 0; stack != null && i < stack.length; i++)
								{
									periodicalDatabaseUpdaterLog.error(stack[i].toString());
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
					periodicalDatabaseUpdaterLog.error(stack[i].toString());
				}
			} 
			
			try 
			{
				Thread.sleep(1000L * 60L * 10L * period);
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
