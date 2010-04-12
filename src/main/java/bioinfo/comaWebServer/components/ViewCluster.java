package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.Cluster;

public class ViewCluster 
{
	@Parameter(required = true)
	private Cluster cluster;

	public Cluster getCluster() 
	{
		return cluster;
	}

	public void setCluster(Cluster cluster) 
	{
		this.cluster = cluster;
	}
	
}
