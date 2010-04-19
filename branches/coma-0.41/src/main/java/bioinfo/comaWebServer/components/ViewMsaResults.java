package bioinfo.comaWebServer.components;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.pages.show.ShowInfo;
import bioinfo.comaWebServer.util.AttachmentStreamResponse;

@IncludeStylesheet("context:assets/styles.css")
public class ViewMsaResults 
{
	@Parameter(required = true)
	private Job job;
	
	@InjectPage
	private ShowInfo infoPage;
	
	Object onException(Throwable cause)
    {
    	infoPage.setUp("", "We are sorry but threre was a fatal error when handling query.");

        return infoPage;
    }
	
	@Persist
	private String faContent;

	public void setJob(Job job) {
		this.job = job;
	}

	public Job getJob() {
		return job;
	}
	
	public StreamResponse onActionFromMsaResults(String id) throws Exception
	{
		InputStream input = null;

		String path = resultsPath();
		
		File file = new File(path);

		byte[] data = FileUtils.readFileToByteArray(file);

		input = new ByteArrayInputStream(data);
        
        return new AttachmentStreamResponse(input, id, "fa");				
	}

	public void setPdbContent(String faContent) 
	{
		this.faContent = faContent;
	}

	public String getFaContent() throws IOException 
	{

		StringBuffer content = new StringBuffer();
		
		String path = resultsPath();
		
		FileInputStream fstream = new FileInputStream(path);
	    
	    DataInputStream in = new DataInputStream(fstream);
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    
	    String strLine;
	    
	    while ((strLine = br.readLine()) != null)   
	    {
	    	content.append(strLine + "\n");
	    }

	    in.close();
	    fstream.close();
	    
	    faContent = content.toString();
		
		return faContent;
	}
	
	private String resultsPath()
	{
		return Cache.getClusterParams().getGlobalFilePath() + job.getGeneratedId() + 
							File.separator + job.getGeneratedId() +
									Extentions.OUTPUT_MSA.getExtention();
	}
}
