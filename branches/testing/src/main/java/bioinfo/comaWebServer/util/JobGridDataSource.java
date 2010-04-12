package bioinfo.comaWebServer.util;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;

public class JobGridDataSource implements GridDataSource 
{
	private List<Job> selectedJobs = null;
	private int start = 0;
	private IDataSource dataSource = null;
	
	
	public JobGridDataSource(IDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	public int getAvailableRows() 
	{
		int jobNumber = 0;
			
		try 
		{
			Long tmp = dataSource.jobNumber();
			jobNumber = tmp.intValue();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return jobNumber;
	}

	@Override
	public Class getRowType() 
	{
		return Job.class;
	}

	@Override
	public Object getRowValue(int arg0) 
	{
		return selectedJobs.get(arg0 - start);
	}

	@Override
	public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) 
	{
    	try
    	{
    		selectedJobs = dataSource.getJobs(startIndex, endIndex, sortConstraints);
			start = startIndex;
		}
    	catch (Exception e)
    	{
    		e.printStackTrace();
		}
	}

}
