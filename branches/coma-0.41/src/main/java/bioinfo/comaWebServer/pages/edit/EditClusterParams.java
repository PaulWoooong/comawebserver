package bioinfo.comaWebServer.pages.edit;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import ch.ethz.ssh2.Connection;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.dataManagement.transfer.PublicKeyAuthentication;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@IncludeStylesheet("context:assets/styles.css")
@Secured("ROLE_ADMIN")
public class EditClusterParams 
{
	@Inject
	private IDataSource dataSource;

	private Cluster cluster;
	
	public void onActivate()
	{
		cluster = Cache.getClusterParams();
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	@OnEvent(component="UpdateClusterParamsForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onUpdateClusterParamsForm()
	{
		String info = "Update: Ok<br/>";
		info += dataSource.updateParams(cluster);
		Cache.refreshClusterParams();
		
		if(!cluster.isLocal())
		{
			try 
			{
				Connection conn = PublicKeyAuthentication.connect(cluster.getUsername(), 
						cluster.getHostname(), cluster.getPrivateKeyPath(), cluster.getPassphrase());
				if(conn != null)
				{
					conn.close();	
				}
			} 
			catch (Exception e) 
			{
				info = e.getMessage();
				e.printStackTrace();
			}
		}

		infoPage.setUp(info, "Updating cluster params:");
		
		return infoPage;
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Cluster getCluster() 
	{
		if(cluster == null)
		{
			cluster = new Cluster();
		}
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

}
