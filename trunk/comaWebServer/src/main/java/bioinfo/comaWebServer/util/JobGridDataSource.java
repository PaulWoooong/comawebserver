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
	private int from = 0;
	private IDataSource dataSource = null;
	
	
	public JobGridDataSource(IDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	@Override
	public int getAvailableRows() 
	{
		String queryStr = "select count(*) from " + JOB_TABLE;
		
		Session session = InitSessionFactory.getInstance().getCurrentSession();
	    Transaction transaction = session.beginTransaction();

		Query query = session.createQuery(queryStr);
		List list = query.list();
		
		transaction.commit();

		return ((Long)list.get(0)).intValue();
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
			from = startIndex;
			transaction.commit();
		}
    	catch (HibernateException e)
    	{
    		if(transaction != null) transaction.rollback();
    		throw e;
		}
	}

}
