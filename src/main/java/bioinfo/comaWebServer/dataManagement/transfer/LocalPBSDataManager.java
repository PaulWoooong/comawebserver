package bioinfo.comaWebServer.dataManagement.transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;


public class LocalPBSDataManager extends AbstractPBSDataManager
{
	
	public Map<String, String> getJobStatus(String username) throws Exception 
	{
		Map<String, String> data = new HashMap<String, String>();
		
		Runtime runtime = Runtime.getRuntime();
		
		Process process = runtime.exec(statusCommand(username));
		
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line;
		
		while((line = input.readLine()) != null)
		{
			String id = getJobWorkstationId(line);
			String status = getJobWorkstationStatus(line);
			
			if(id != null && status != null)
			{
				data.put(id, status);
			}
		}

		input.close();
		
		return data;
	}

	public String submitJob(String command) throws Exception 
	{
		Runtime runtime = Runtime.getRuntime();
		
		Process process = runtime.exec(command);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String id = getJobSubmissionId(input.readLine());
		
		input.close();
		
		return id;
	}
	
	public void retieveData(Collection<String> files, String dir) throws Exception{}

	public void sendData(Collection<String> files, String dir) throws Exception{}

	public void cancelJob(String generatedId) throws Exception 
	{
		Runtime runtime = Runtime.getRuntime();
		
		runtime.exec(cancelCommand(generatedId));
	}

	public void deleteDir(String path) throws Exception 
	{
		File file = new File(path);
		
		if(file.exists())
		{
			FileUtils.forceDelete(file);	
		}
	}

	public void createDir(String path) throws Exception {}

	public boolean fileExists(String path) throws Exception 
	{
		File file = new File(path);
		
		if(file.exists())
		{
			return true;	
		}
		
		return false;
	}
	
	public String fileLastModified(String path) throws Exception
	{
		File file  = new File(path);

		if(!file.exists())
		{
			throw new Exception("'File not found: " + path + "'");
		}
		
		Date d = new Date(file.lastModified());
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    
	    int month 	= cal.get(Calendar.MONTH) + 1;
	    int day	  	= cal.get(Calendar.DAY_OF_MONTH);
	    
	    String dayStr = String.valueOf(day);
	    if(day < 10)
	    {
	    	dayStr = "0" + dayStr;
	    }
	    String monthStr = String.valueOf(month);
	    if(month < 10)
	    {
	    	monthStr = "0" + monthStr;
	    }

		return cal.get(Calendar.YEAR) + "-" + monthStr + "-" + dayStr;
	}
}
