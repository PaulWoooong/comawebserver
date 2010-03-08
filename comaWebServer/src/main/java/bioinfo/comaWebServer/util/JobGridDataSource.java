package bioinfo.comaWebServer.util;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.services.InitSessionFactory;

public class JobGridDataSource implements GridDataSource 
{
	private static final String JOB_TABLE = " Job ";
	private List<Job> selectedJobs = null;
	private int start = 0;
	private int end = 0;
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
		return selectedJobs.get(arg0);
	}

	@Override
	public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) 
	{
	    Transaction transaction = null;
	    Session session = InitSessionFactory.getInstance().getCurrentSession();

    	try
    	{
			transaction = session.beginTransaction();
			Query query = session.createQuery("from " + JOB_TABLE + " o ");
			selectedJobs = query.list();
			start = startIndex;
			end = endIndex;
			transaction.commit();
		}
    	catch (HibernateException e)
    	{
    		if(transaction != null) transaction.rollback();
    		throw e;
		}
	}

}
