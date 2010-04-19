package bioinfo.comaWebServer.dataManagement.transfer;

import java.util.Collection;
import java.util.Map;

public interface IDataManager 
{
	public void createDir(String path) throws Exception;
	public void deleteDir(String path) throws Exception;
	
	public boolean fileExists(String path) throws Exception;
	public String fileLastModified(String path) throws Exception;
	
	public void sendData(Collection<String> files, String dir) throws Exception;
	public void retieveData(Collection<String> files, String dir) throws Exception;
	
	public String submitJob(String command) throws Exception;
	public void cancelJob(String generatedId) throws Exception;
	public Map<String, String> getJobStatus(String username) throws Exception;
}
