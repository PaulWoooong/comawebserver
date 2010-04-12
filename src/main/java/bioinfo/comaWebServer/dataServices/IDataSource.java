package bioinfo.comaWebServer.dataServices;

import java.util.List;
import java.util.Set;

import org.apache.tapestry5.grid.SortConstraint;

import bioinfo.comaWebServer.entities.Cluster;
import bioinfo.comaWebServer.entities.ComaParams;
import bioinfo.comaWebServer.entities.ComaResults;
import bioinfo.comaWebServer.entities.EmailNotification;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.entities.User;

public interface IDataSource 
{
	public ComaParams getComaParams() throws Exception;
	public void update(ComaParams comaParams) throws Exception;
	
	public Search getSearchParams();
	public String   updateParams(Search search);
	
	public Cluster getClusterParams();
	public String   updateParams(Cluster cluster);
	
	public PeriodicalWorkerParams getPeriodicalWorkerParams();
	public String   updateParams(PeriodicalWorkerParams periodicalWorkerParams);
	
	public EmailNotification getEmailNotificationParams();
	public void updateParams(EmailNotification emailNotification);
	
	public void saveOrUpdate(DatabaseItem db) throws Exception;
	public void delete(DatabaseItem db) throws Exception;
	public void deletePSIBlastDatabase(long id) throws Exception;
	
	public List<DatabaseItem> getDatabases(String type) throws Exception;
	public DatabaseItem getDatabase(long id) throws Exception;
	
	public void saveOrUpdate(User user) throws Exception;
	public void delete(User user) throws Exception;
	
	public Job registerJob(String prefix) throws Exception;
	public void save(Job job) throws Exception;
	public void update(Job job) throws Exception;
	public void delete(Job job) throws Exception;
	public void deleteJob(long id) throws Exception;
	public String jobStatus(String generatedId) throws Exception;
	public Long jobNumber() throws Exception;
	public List<Job> getJobs(int start, int end,  List<SortConstraint> sortConstraints) throws Exception;
	public List<Job> getJobs() throws Exception;
	public Job getJob(long id) throws Exception;
	public Job getJobByGeneratedId(String id) throws Exception;
	public Job getJobByGeneratedIdORDescription(String info) throws Exception;
	
	public ComaResults getComaResultsById(long id) throws Exception;
	
	public ResultsAlignment getResultsAlignment(long id) throws Exception;

	public Set<Job> getExpiredJobs() throws Exception;
	public Set<Job> getNotFinishedJobs() throws Exception;
	
	public long runningJobsNumber() throws Exception;
	
	public void initializeSystem();
}
