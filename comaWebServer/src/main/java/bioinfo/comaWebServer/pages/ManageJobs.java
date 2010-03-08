package bioinfo.comaWebServer.pages;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.pages.show.ShowResults;

@Secured("ROLE_ADMIN")
public class ManageJobs 
{
	@Inject
	private IDataSource dataSource;

	private Job job;
	
	public List<Job> getJobs() throws Exception
	{
		return dataSource.getJobs();
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Job getJob() {
		return job;
	}
	
	@InjectPage
	private ShowResults resultsPage;
	
	Object onActionFromView(String id)
    {
		resultsPage.setUp(id);
        return resultsPage;
    } 
}
