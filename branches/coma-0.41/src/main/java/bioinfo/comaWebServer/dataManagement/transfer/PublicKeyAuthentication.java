package bioinfo.comaWebServer.dataManagement.transfer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class PublicKeyAuthentication
{
	private static final Logger PublicKeyAuthenticationLog = Logger.getLogger("publicKeyAuthentication");

	private static Connection connection = null;
	
	private PublicKeyAuthentication(){}
	
	public static synchronized Connection connect(String username, String hostname, String keyFilePath, String keyFilePass) throws Exception
	{
		if(connection != null)
		{
			try 
			{
				Session session = connection.openSession();
				session.close();
			} 
			catch (Exception e) 
			{
				disconnect();
				
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					PublicKeyAuthenticationLog.error(stack[i].toString());
				}
				
				connection = createConnection(username, hostname, keyFilePath, keyFilePass);
			}
		}
		else
		{
			connection = createConnection(username, hostname, keyFilePath, keyFilePass);
		}
		
		return connection;
	}
	
	public static synchronized void disconnect()
	{
		if(connection != null)
		{
			connection.close();
			connection = null;
		}
	}
	
	private static synchronized Connection createConnection(String username, String hostname, String keyFilePath, String keyFilePass) throws Exception
	{
		File keyfile = new File(keyFilePath);

		Connection conn = null;

		conn = new Connection(hostname);

		conn.connect();

		boolean isAuthenticated = conn.authenticateWithPublicKey(username, keyfile, keyFilePass);

		if (isAuthenticated == false)
		{
			throw new IOException("Authentication failed.");
		}

		return conn;
	}
}
