package bioinfo.comaWebServer.pages;

import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.util.EnumSelectModel;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.AbstractParameter;
import bioinfo.comaWebServer.entities.ComaParams;
import bioinfo.comaWebServer.entities.Input;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.RecentJobs;
import bioinfo.comaWebServer.entities.Search;
import bioinfo.comaWebServer.enums.InputType;
import bioinfo.comaWebServer.enums.PsiBlastRadioParams;
import bioinfo.comaWebServer.jobManagement.JobSubmitter;
import bioinfo.comaWebServer.pages.show.ShowInfo;
import bioinfo.comaWebServer.util.CookieManager;
import bioinfo.comaWebServer.util.Validator;

/**
 * Start page of application comaWebServer.
 */
@IncludeStylesheet("context:assets/styles.css")
public class Index
{
	
	private static final Logger indexLog = Logger.getLogger("index");
	
	@ApplicationState
	private RecentJobs recentJobs;
	private boolean recentJobsExists;
	
	@Inject
	private IDataSource dataSource;
	
	@Property
	@Persist
	private ComaParams comaParams;
	
	@Property
	@Persist
	private Search search;

	@Persist
	private List<DatabaseItem> alignmentDB;
	@Persist
	private List<DatabaseItem> sequenceDB;
	
	@Inject
	private Messages messages;
	public SelectModel getTypes()
	{
	  return new EnumSelectModel(InputType.class, messages);
	}
	
	@Property
	@Persist
	private Input input;
	
	@Persist
	private boolean init;
	
	public void onPrepare() throws Exception
	{
		if(!init)
		{
			init 					= true;
			
			comaParams				= dataSource.getComaParams();
			
			search 					= dataSource.getSearchParams();
			alignmentDB				= dataSource.getDatabases(DatabaseItem.PROFILE_DB);
			sequenceDB 				= dataSource.getDatabases(DatabaseItem.SEQUENCE_DB);
			
			input					= new Input();
		}
	}
	
	@OnEvent(component="newJobButton", value = "selected")
	void onNewJobButton()
	{
		initResultsPage = true;
	}
	
	@OnEvent(component="resetButton", value = "selected")
	void onResetButton()
	{
		init = false;
		initResultsPage = false;
	}
	
	@Component
	private Form submitJobForm;
	@Component(id = "sequence")
	private TextArea txtAreaSeq;
	
	@InjectPage
	private WaitForResults waitForResults;
	
	@InjectPage
	private ShowInfo infoPage;
	
	@Persist
	private boolean initResultsPage;
	
	Object onSuccess()
	{	
		if(initResultsPage)
		{	
			initResultsPage = false;

			if(!Validator.checkAlignment_G(comaParams.getAlignment().getUc_G()))
			{
				submitJobForm.recordError("Alignment gap opening cost: 0 or A[1,50]");
				return null;
			}
			
			if(input.getFile() == null && input.getSequence() == null)
			{
				submitJobForm.recordError(txtAreaSeq, "You must specify an input source!");
				return null;
			}
			
			if(search.getMsaStrategy() == PsiBlastRadioParams.RR)
			{
				if(search.getLc_h() == 0)
				{
					submitJobForm.recordError("E-value threshold for inclusion must be provided!");
					return null;
				}
				
				if(search.getLc_j() == 0)
				{
					submitJobForm.recordError("Maximal number of iterations  must be provided!");
					return null;
				}
			}
			
			String id = "";
			
			try
			{
				List<AbstractParameter> params = comaParams.getParams();
				params.add(search);
				JobSubmitter jobSubmitter = new JobSubmitter();
				id = jobSubmitter.submitComaJob(input, 
												params, 
												dataSource, 
												search,
												comaParams.getAlignment().getDatabaseItem());
				recentJobs.addJob(dataSource.getJobByGeneratedId(id));
				CookieManager.registerJob(id, cookies);
			}
			catch(IOException e)
			{
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					indexLog.error(stack[i].toString());
				}
				
				infoPage.setUp("",  "IOException has occured! " +
									"System is unavailable for the moment!");

				return infoPage;
			}
			catch (Exception e) 
			{		
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; stack != null && i < stack.length; i++)
				{
					indexLog.error(stack[i].toString());
				}
				infoPage.setUp("", e.getMessage());
				
				return infoPage;
			}

			init = false;
			
			waitForResults.setUp(id);
			
			return waitForResults;
		}
		
		return null;
	}

	Object onUploadException(FileUploadException ex)
    {
		infoPage.setUp("", "File is too large. Max 1 MB.");
        
        return infoPage;
    }
    
    Object onException(Throwable cause)
    {

    	infoPage.setUp("", "We are sorry but the service is temporary unavailable.");

        return infoPage;
    }

	public List<DatabaseItem> getAlignmentDB() {
		return alignmentDB;
	}

	public void setAlignmentDB(List<DatabaseItem> alignmentDB) {
		this.alignmentDB = alignmentDB;
	}

	public List<DatabaseItem> getSequenceDB() {
		return sequenceDB;
	}

	public void setSequenceDB(List<DatabaseItem> sequenceDB) {
		this.sequenceDB = sequenceDB;
	}

	public RecentJobs getRecentJobs() {
		return recentJobs;
	}

	public void setRecentJobs(RecentJobs recentJobs) {
		this.recentJobs = recentJobs;
	}

	public boolean isRecentJobsExists() {
		return recentJobsExists;
	}

	public void setRecentJobsExists(boolean recentJobsExists) {
		this.recentJobsExists = recentJobsExists;
	}
	
	@Inject
	private Cookies cookies;

}
