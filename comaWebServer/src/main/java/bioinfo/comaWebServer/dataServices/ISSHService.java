package bioinfo.comaWebServer.dataServices;

import java.io.File;
import java.io.IOException;
import java.util.List;

import bioinfo.comaWebServer.exceptions.InitializationException;
import bioinfo.comaWebServer.exceptions.JobFailureException;
import bioinfo.comaWebServer.exceptions.SSHConnectioException;

public interface ISSHService

{
	public IConnection connect() throws SSHConnectioException, InitializationException;
	
	public void uploadFiles(IConnection connection, 
							   List<String> localFiles, 
							   String remoteTargetDir) throws IOException;

	public void uploadFiles(IConnection connection, 
							   String localFile, 
							   String remoteTargetDir) throws IOException;
	
	public String submit(IConnection connection,
									  File data, 
									  File params, 
									  String command, 
									  String jobId, 
									  String path) throws Exception;
	public void deleteDir(IConnection connection, 
							 String path) throws IOException;
	public void createDir(IConnection connection, 
			 				 String path) throws IOException;
	
	public String jobStatus(IConnection connection, 
							  String command) throws IOException;
	
	public void downloadFiles(IConnection connection, 
								   List<String> remoteFiles, 
								   String localTargetDir) throws IOException;
	
	public void downloadFiles(IConnection connection, 
								   String remoteFile, 
								   String localTargetDir) throws IOException;
	
	public void testFile(IConnection connection, 
			   				String command) throws JobFailureException, IOException;
	
	public String fileModificationTime(IConnection connection, String file) throws IOException;
}
