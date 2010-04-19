package bioinfo.comaWebServer.dataManagement.transfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemotePBSDataManager extends AbstractPBSDataManager
{
	private Connection connection;
	
	public RemotePBSDataManager(Connection connection)
	{
		this.connection = connection;
	}
	
	public Map<String, String> getJobStatus(String username) throws Exception
	{
		Map<String, String> data = new HashMap<String, String>();
		
		Session session = connection.openSession();
		session.execCommand(statusCommand(username));

		try 
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				throw new Exception(err.toString());
			}
			
			List<String> stdout = getStdout(session);
			
			for(String line: stdout)
			{
				String id = getJobWorkstationId(line);
				String status = getJobWorkstationStatus(line);
				
				if(id != null && status != null)
				{
					data.put(id, status);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			session.close();
		}

		return data;
	}

	public String submitJob(String command) throws Exception
	{
		Session session = (connection).openSession();
		session.execCommand(command);

		String id = null;
		
		try
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				throw new Exception(err.toString());
			}
			
			List<String> stdout = getStdout(session);

			id = getJobSubmissionId(stdout.get(0));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			session.close();
		}
		
		return id;
	}
		
	public void retieveData(Collection<String> files, String dir) throws Exception
	{
		SCPClient scp = new SCPClient(connection);
		
		String[] f = (String[])files.toArray(new String[0]);
	
		scp.get(f, dir);
	}

	public void sendData(Collection<String> files, String dir) throws Exception
	{
		SCPClient scp = new SCPClient(connection);
		
		String[] f = (String[])files.toArray(new String[0]);
			
		scp.put(f, dir);
	}
	
	public void cancelJob(String generatedId) throws Exception 
	{
		Session session = (connection).openSession();
		session.execCommand(cancelCommand(generatedId));
		
		getStderr(session);
		getStdout(session);

		session.close();
	}

	public void createDir(String path) throws Exception 
	{
		Session session = (connection).openSession();
		session.execCommand("mkdir -p " + path);

		try
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				throw new Exception(err.toString());
			}
			getStdout(session);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			session.close();
		}
	}
	
	public String fileLastModified(String path) throws Exception
	{
		Session session = (connection).openSession();
		session.execCommand("stat -c %y " + path);

		String date = null;
		
		try
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				throw new Exception(err.toString());
			}
			
			List<String> stdout = getStdout(session);
			
			String[] infoDetails = stdout.get(0).split("\\s+");
			
			if(infoDetails.length > 0)
			{
				date = infoDetails[0];
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			session.close();
		}
		
		return date;
	}

	public void deleteDir(String path) throws Exception 
	{
		Session session = (connection).openSession();
		session.execCommand("rm -fr " + path);

		try
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				throw new Exception(err.toString());
			}
			
			getStdout(session);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			session.close();
		}
	}
	
	public boolean fileExists(String path) throws Exception 
	{
		Session session = (connection).openSession();
		session.execCommand("ls " + path);

		boolean exists = true;
		
		try
		{
			String err = getStderr(session);
			
			if(!err.equals(""))
			{
				exists = false;
			}
			
			getStdout(session);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			session.close();
		}
		
		return exists;
	}
	
	private static String getStderr(Session session) throws IOException
	{
		InputStream stderr = new StreamGobbler(session.getStderr());
		BufferedReader error = new BufferedReader(new InputStreamReader(stderr));
		
		StringBuffer err = new StringBuffer("");
		
		String line;
		
		try 
		{
			while((line = error.readLine()) != null)
			{
				err.append(line + "\n");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			error.close();	
		}
		
		return err.toString();
	}
	
	private static List<String> getStdout(Session session) throws IOException
	{
		InputStream stdout = new StreamGobbler(session.getStdout());
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

		List<String> data = new ArrayList<String>();
		
		String line;
		
		try 
		{
			while((line = reader.readLine()) != null)
			{
				data.add(line);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			
			throw e;
		}
		finally
		{
			reader.close();	
		}
		
		return data;
	}

}
