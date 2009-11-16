package bioinfo.comaWebServer.dataServices;

import ch.ethz.ssh2.Connection;

public class ConnectionAdapter implements IConnection 
{
	private Connection connection = null;
	
	public ConnectionAdapter(Connection connection)
	{
		this.connection = connection;
	}

	public Connection getConnection() 
	{
		return connection;
	}

	public void disconnect() 
	{
		if(connection != null)
		{
			connection.close();
			connection = null;
		}
	}

}
