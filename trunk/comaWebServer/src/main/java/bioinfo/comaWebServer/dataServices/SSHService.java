package bioinfo.comaWebServer.dataServices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.KnownHosts;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.ServerHostKeyVerifier;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.enums.Commands;
import bioinfo.comaWebServer.exceptions.CommandException;
import bioinfo.comaWebServer.exceptions.InitializationException;
import bioinfo.comaWebServer.exceptions.JobFailureException;
import bioinfo.comaWebServer.exceptions.SSHConnectioException;

public class SSHService implements ISSHService
{
	private KnownHosts database = null;
	private static final Logger sshServiceLog = Logger.getLogger("sshService");
	
	public void testFile(IConnection connection, 
							String command) throws JobFailureException, IOException
	{
		try 
		{
			executeCommand(connection, command);
		} 
		catch (CommandException e) 
		{
			throw new JobFailureException();
		}
	}
	
	public String fileModificationTime(IConnection connection, String file) throws IOException 
	{
		String command = Commands.FILE_MODIFICATION.getValue() + " " + file;
		String info = executeCommand(connection, command);
		String[] infoDetails = info.split("\\s+");
		
		String date = null;
		if(infoDetails.length > 0)
		{
			date = infoDetails[0];
		}

		return date;
	}
	
	public void uploadFiles(IConnection connection, List<String> localFiles,
									String remoteTargetDir) throws IOException 
	{
		SCPClient scp = new SCPClient((Connection) connection.getConnection());
		
		String[] rf = new String[localFiles.size()];
		
		for(int i = 0; i < rf.length; i++)
		{
			rf[i] = localFiles.get(i);
		}
			
		scp.put(rf, remoteTargetDir);
	}

	public void uploadFiles(IConnection connection, String localFile,
									String remoteTargetDir) throws IOException 
	{
		SCPClient scp = new SCPClient((Connection) connection.getConnection());
		
		scp.put(localFile, remoteTargetDir);
	}
	
	public void downloadFiles( IConnection connection, 
								   String remoteFile, 
								   String localTargetDir) throws IOException 
	{
		SCPClient scp = new SCPClient((Connection) connection.getConnection());
		
		scp.get(remoteFile, localTargetDir);
	}
	
	public void downloadFiles(IConnection connection, 
								   List<String> remoteFiles, 
								   String localTargetDir) throws IOException 
	{
		SCPClient scp = new SCPClient((Connection) connection.getConnection());
		
		String[] rf = new String[remoteFiles.size()];
		
		for(int i = 0; i < rf.length; i++)
		{
			rf[i] = remoteFiles.get(i);
		}
			
		scp.get(rf, localTargetDir);
	}

	public String jobStatus(IConnection connection, 
							  String command) throws IOException 
	{
		String status = null;
		try 
		{
			status = executeCommand(connection, command);
		}
		catch (CommandException e) 
		{
			return Job.FINISHED;
		}
		catch (Exception e) 
		{
			return Job.WAITING;
		}

		return extractJobStatus(status);
	}
	
	private String extractJobStatus(String info)
	{
		String status = Job.WAITING;
		
		String[] msgs = info.split("\\s+");
		
		String st = msgs[msgs.length - 2];
		
		if(st.equals("R"))
		{
			status = Job.RUNNING;
		}
		else if(st.equals("C") || st.equals("E"))
		{
			status = Job.FINISHED;
		}
		
		return status;
	}

	public String submit(	IConnection connection,
							File data, 
							File params,
							String command,
							String jobId,
							String path) throws Exception
	{
		SCPClient scp = new SCPClient((Connection) connection.getConnection());
		if(data != null) 
		{
			scp.put(data.getAbsolutePath(), path);
		}

		if(params != null) 
		{
			scp.put(params.getAbsolutePath(), path);
		}

		String id = executeCommand(connection, command);

		return id;
	}
	
	public void deleteDir(IConnection connection, 
							 String path) throws IOException
	{
		Session session = ((Connection) connection.getConnection()).openSession();
		InputStream stderr = new StreamGobbler(session.getStderr());
		session.execCommand("rm -fr " + path);

		while (stderr.read() != -1){}

		session.close();
	}
	
	public void createDir(IConnection connection, 
			 					String path) throws IOException
	{
		Session session = ((Connection) connection.getConnection()).openSession();
		InputStream stderr = new StreamGobbler(session.getStderr());
		session.execCommand("mkdir -p " + path);
		while (stderr.read() != -1){}
		session.close();
	}
	
	private String executeCommand(IConnection connection,
								  	String command) throws IOException
	{
		String info = null;
		
		Session session = ((Connection) connection.getConnection()).openSession();
		session.execCommand(command);
		
		InputStream stderr = new StreamGobbler(session.getStderr());
		int errStatus = stderr.read();
		
		String err = "";
		
		while (errStatus != -1)
		{
			err += (char) errStatus;
			errStatus = stderr.read();
		}

		if(!err.equals(""))
		{
			throw new CommandException("Command exception: " + err);
		}
		
		InputStream stdout = new StreamGobbler(session.getStdout());
		int status = stdout.read();
		info = new String();
		
		while(status != -1)
		{
			info += (char)status;
			status = stdout.read();
		}
		session.close();

		return info;
	}

	public IConnection connect() throws InitializationException
	{
		Connection connection = null;

		try 
		{
			connection = connectToServer();
		}
		catch (InitializationException e)
		{
			throw e;
		}
		catch (Exception e) 
		{
			StackTraceElement[] stact = e.getStackTrace();
			for(int i = 0; stact != null && i < stact.length; i++)
			{
				sshServiceLog.error(stact[i].toString());
			}
		}
		
		if(connection == null)
		{
			throw new SSHConnectioException();
		}
		
		return new ConnectionAdapter(connection);
	}
	
	private Connection connectToServer() throws InitializationException 
	{
		Cluster clusterParams = Cache.getClusterParams();
		
		if(clusterParams == null)
		{
			throw new InitializationException("The system has not been initialized yet!");
		}

		database = new KnownHosts();

		File knownHostFile = new File("");
		if (knownHostFile.exists()) 
		{
			try 
			{
				database.addHostkeys(knownHostFile);
			} 
			catch (IOException e){}
		}

		Connection connection = new Connection(clusterParams.getHostname(), 
														clusterParams.getPort());

		if (clusterParams.getUsername() == null
				&& clusterParams.getPrivateKeyPath() == null
				&& clusterParams.getPassphrase() == null) 
		{
			throw new InitializationException("The system has not been initialized yet!");
		}

		String[] hostkeyAlgos = database
				.getPreferredServerHostkeyAlgorithmOrder(clusterParams
						.getHostname());

		if (hostkeyAlgos != null) 
		{
			connection.setServerHostKeyAlgorithms(hostkeyAlgos);
		}

		try 
		{
			connection.connect(new AdvancedVerifier());
		} 
		catch (IOException e) 
		{
			StackTraceElement[] stact = e.getStackTrace();
			for(int i = 0; stact != null && i < stact.length; i++)
			{
				sshServiceLog.error(stact[i].toString());
			}
			
			throw new SSHConnectioException();
		}

		/*
		 * 
		 * AUTHENTICATION PHASE
		 */

		boolean connected = false;

		try 
		{
			if (connection.isAuthMethodAvailable(clusterParams.getUsername(),
					"publickey")) 
			{
				if (clusterParams.getPrivateKeyPath() != null) 
				{
					File key = new File(clusterParams.getPrivateKeyPath());

					if (key.exists()) 
					{
						if (clusterParams.getPassphrase() == null) 
						{
							clusterParams.setPassphrase("");
						}
						connected = connection.authenticateWithPublicKey(
								clusterParams.getUsername(), key,
								clusterParams.getPassphrase());
					}
				}
			}
		} 
		catch (IOException e) 
		{
			StackTraceElement[] stact = e.getStackTrace();
			for(int i = 0; stact != null && i < stact.length; i++)
			{
				sshServiceLog.error(stact[i].toString());
			}
		}

		if (!connected) 
		{
			throw new SSHConnectioException();
		}
		
		return connection;
	}

	/**
	 * This ServerHostKeyVerifier asks the user on how to proceed if a key
	 * cannot be found in the in-memory database.
	 * 
	 */
	class AdvancedVerifier implements ServerHostKeyVerifier 
	{
		public boolean verifyServerHostKey(String hostname, int port,
				String serverHostKeyAlgorithm, byte[] serverHostKey)
				throws Exception 
		{
			/* Check database */

			int result = database.verifyHostkey(hostname,
					serverHostKeyAlgorithm, serverHostKey);

			switch (result) 
			{
			case KnownHosts.HOSTKEY_IS_OK:
				return true;

			case KnownHosts.HOSTKEY_IS_NEW:
				break;

			case KnownHosts.HOSTKEY_HAS_CHANGED:
				break;

			default:
				throw new IllegalStateException();
			}

			return true;
		}
	}
}
