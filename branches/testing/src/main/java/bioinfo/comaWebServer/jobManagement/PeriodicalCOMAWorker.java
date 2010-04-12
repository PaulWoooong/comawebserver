package bioinfo.comaWebServer.jobManagement;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.comparators.ResultsAlignmentComparator;
import bioinfo.comaWebServer.dataManagement.periodical.AbstractPeriodicalWorker;
import bioinfo.comaWebServer.dataManagement.transfer.IDataManager;
import bioinfo.comaWebServer.dataServices.ComaResultsParser;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.dataServices.ImageProcessor;
import bioinfo.comaWebServer.entities.AlignmentBibliography;
import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.DataFile;
import bioinfo.comaWebServer.entities.Image;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.ResultsQuery;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.JobType;
import bioinfo.comaWebServer.exceptions.JobFailureException;

public class PeriodicalCOMAWorker extends AbstractPeriodicalWorker 
{
	public PeriodicalCOMAWorker(IDataSource dataSource) 
	{
		super(dataSource);
	}

	protected String jobSubmission(IDataManager dataManager,
			boolean localWorkstation, String globalFilePath,
			String remoteFilePath, String jobGeneratedId,
			Set<DataFile> dataFiles, String jobType) throws Exception 
	{
		Cluster cluster = Cache.getClusterParams();
		
		String workstationId = null;
		
		String path = null;
		
		if(localWorkstation)
		{
			path = globalFilePath + jobGeneratedId;
		}
		else
		{
			path = remoteFilePath + jobGeneratedId;
		}
		
		String command = null;
		
		if(jobType.equals(JobType.COMA_JOB.getType()))
		{
			command = Cluster.getComaCommand(cluster.getCommandComa(), path, jobGeneratedId);
		}
		else if(jobType.equals(JobType.MODELLER_JOB.getType()))
		{
			command = Cluster.getComaCommand(cluster.getCommandModeller(), path, jobGeneratedId);
		}
		else if(jobType.equals(JobType.MSA_JOB.getType()))
		{
			command = Cluster.getComaCommand(cluster.getCommandMsa(), path, jobGeneratedId);
		}
		else
		{
			throw new Exception("Don't know the job type: " + jobType);
		}
		
		if(localWorkstation)
		{
			workstationId = dataManager.submitJob(command);
		}
		else
		{
			String remoteJobDir = remoteFilePath + jobGeneratedId;
			
			dataManager.createDir(remoteJobDir);
			
			List<String> files = new ArrayList<String>();
			
			for(DataFile file: dataFiles)
			{
				if(file.getType().equals(DataFile.INPUT))
				{
					files.add(globalFilePath + jobGeneratedId + File.separator + file.getPath());					
				}
			}
		
			dataManager.sendData(files, remoteJobDir);
			
			workstationId = dataManager.submitJob(command);
		}
		
		return workstationId;
	}

	protected void resultsExtraction(IDataManager dataManager, Job job,
			String remotePath, String localPath) throws Exception 
	{
		Cluster cluster = Cache.getClusterParams();
		
		if(!cluster.isLocal())
		{
			List<String> files = new ArrayList<String>();
			
			for(DataFile file: job.getDataFiles())
			{
				if(file.getType().equals(DataFile.OUTPUT))
				{
					String filePath = remotePath + job.getGeneratedId() + "/" + file.getPath();
					if(dataManager.fileExists(filePath))
					{
						files.add(filePath);	
					}					
				}
			}
			
			dataManager.retieveData(files, localPath + job.getGeneratedId());
		}
		
		String errorMsg = getErrorInfo(localPath + job.getGeneratedId() + File.separator + job.getGeneratedId() + Extentions.ERR.getExtention());
		if(!errorMsg.equals(""))
		{
			throw new JobFailureException("Job ID: " + job.getGeneratedId());
		}
		
		if(job.getType() == JobType.COMA_JOB)
		{
			ComaResults comaResults = job.getComaResults();
			
			ComaResultsParser.parse(comaResults, job.getLocalFilePath(Extentions.OUTPUT_COMA_OUT.getExtention()));
			
			comaResults.setNumberOfSequences(ComaResultsParser.numberOfSeq(job.getLocalFilePath(Extentions.OUTPUT_COMA_MA.getExtention())));
			
			Map<String, AlignmentBibliography> ids = ComaResultsParser.parseIDS(job.getLocalFilePath(Extentions.OUTPUT_COMA_ID.getExtention()));
			setBibliography(comaResults.getAlignments(), ids);
			
			int length = 0;
			
			try 
			{
				length = Integer.parseInt(comaResults.getResultsFooter().getQueryLength());
				
				ImageProcessor imageProcessor = new ImageProcessor(length);

				PeriodicalWorkerParams periodicalWorkerParams = Cache.getPeriodicalWorkerParams();
				
				comaResults.setImages(setImages(comaResults.getAlignments(), periodicalWorkerParams,
						imageProcessor, localPath + job.getGeneratedId()));
			} 
			catch (Exception e){}
			
			job.setComaResults(comaResults);
		}
	}
	
	private static Set<Image> setImages(Set<ResultsAlignment> alignments, 
			PeriodicalWorkerParams periodicalWorkerParams, ImageProcessor imageProcessor, 
			String localPath) throws IOException
	{
		Set<Image> images = new HashSet<Image>();
		
		ArrayList<ResultsAlignment> sortedAlignments = new ArrayList<ResultsAlignment>(alignments);
		Collections.sort(sortedAlignments, new ResultsAlignmentComparator());

		int counter = 1;
		for(ResultsAlignment alignment: sortedAlignments)
		{
			boolean ok = true;
			double evalue = Double.MAX_VALUE;
			
			try 
			{
				evalue = Double.parseDouble(alignment.getExpect());
			} 
			catch (NumberFormatException e)
			{
				ok = false;
			}
			
			if(ok && evalue 	<= periodicalWorkerParams.getEvalueForPictures() && 
				counter		 	<= periodicalWorkerParams.getNumberForPictures())
			{
				int mainBegin = Integer.MAX_VALUE;
				int mainEnd	   = Integer.MIN_VALUE;
				
				for(ResultsQuery query: alignment.getQueries())
				{
					try 
					{
						int begin  = Integer.parseInt(query.getQueryBegin());
						int end 	= Integer.parseInt(query.getQueryEnd());
						
						if(mainBegin > begin)
						{
							mainBegin = begin;
						}
						if(mainEnd < end)
						{
							mainEnd = end;
						}
					}
					catch (NumberFormatException e) 
					{
						ok = false;
					}
				}
				
				if(ok)
				{
					//0x5 - hexadecimal format
					Color color = Color.BLUE;
					
					if(evalue <= periodicalWorkerParams.getRedThreshold())
					{
						color = Color.RED;
					}
					else if(evalue <= periodicalWorkerParams.getGreenThreshold())
					{
						color = new Color(51, 153, 0x0);
					}
					
					String header = "";
					
					if(alignment.getAlignmentBibliography() != null)
					{
						if(alignment.getAlignmentBibliography().getTextForImages() != null)
						{
							header = alignment.getAlignmentBibliography().getTextForImages();
						}
					}
					
					imageProcessor.draw(localPath + File.separator + counter + "." + ImageProcessor.extention, color, header, mainBegin - 1, mainEnd - 1);
					images.add(new Image(counter + "." + ImageProcessor.extention, alignment.getPriority()));
					
					counter++;
				}
			} 
		}
		return images;
	}
	
	private static void setBibliography(Set<ResultsAlignment> alignments, Map<String, AlignmentBibliography> ids)
	{	
		for(ResultsAlignment alignment: alignments)
		{
			String alName = alignment.getName();

			boolean found = false;
			
			Iterator<Entry<String, AlignmentBibliography>> it = ids.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		        Map.Entry<String, AlignmentBibliography> pairs = (Map.Entry<String, AlignmentBibliography>)it.next();
		        
		        String biblioName = pairs.getKey();

		        if(biblioName.equals(alName))
		        {
		        	alignment.setAlignmentBibliography(pairs.getValue());
		        	found = true;
		        }
		    }
		    
			if(!found)
			{
				alignment.setAlignmentBibliography(new AlignmentBibliography());
			}
		}
	}
	
	private static String getErrorInfo(String path) throws IOException
	{
		List<String> data = readFile(path);
		
		StringBuffer buffer = new StringBuffer("");
		
		for(String line: data)
		{
			if(!line.equals(""));
			{
				buffer.append(line + "<br/>");
			}
		}
		
		return buffer.toString();
	}
	
	private static List<String> readFile(String path) throws IOException
	{
		List<String> data = new ArrayList<String>();

	    FileInputStream fstream = new FileInputStream(path);

	    DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    while ((strLine = br.readLine()) != null)   
	    {
	      data.add(strLine);
	    }
	    in.close();
	    
		return data;
	}

}
