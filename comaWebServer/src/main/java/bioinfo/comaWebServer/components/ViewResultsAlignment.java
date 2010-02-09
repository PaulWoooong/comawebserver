package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.ResultsQuery;
import bioinfo.comaWebServer.pages.ModellerJob;
import bioinfo.comaWebServer.pages.show.ShowInfo;

public class ViewResultsAlignment 
{
	@Parameter(required = true)
	private ResultsAlignment resultsAlignment;
	
	@Parameter(required = true)
	private boolean showLinks;
	
	@Parameter(required = true)
	private boolean supportModeling;
	
	@InjectPage
	private ModellerJob modellerJob;
	
	Object onActionFromMakeModel(long id) throws Exception
    {
        modellerJob.setValues(id);
        
        return modellerJob;
    }

	@InjectPage
	private ShowInfo infoPage;
	
	Object onException(Throwable cause)
    {
		cause.printStackTrace();
    	infoPage.setUp("", "We are sorry but threre was a fatal error when handling query.");

        return infoPage;
    }
	
	private String hierarchyLink;

	public ResultsAlignment getResultsAlignment() {
		return resultsAlignment;
	}

	public void setResultsAlignment(ResultsAlignment resultsAlignment) {
		this.resultsAlignment = resultsAlignment;
	}
	
	private ResultsQuery resultsQuery;

	public ResultsQuery getResultsQuery() {
		return resultsQuery;
	}

	public void setResultsQuery(ResultsQuery resultsQuery) {
		this.resultsQuery = resultsQuery;
	}

	public boolean isShowLinks() {
		return showLinks;
	}

	public void setShowLinks(boolean showLinks) {
		this.showLinks = showLinks;
	}

	public String getHierarchyLink() {
		return hierarchyLink;
	}

	public void setHierarchyLink(String hierarchyLink) {
		this.hierarchyLink = hierarchyLink;
	}

	public boolean isSupportModeling() {
		return supportModeling;
	}

	public void setSupportModeling(boolean supportModeling) {
		this.supportModeling = supportModeling;
	}
}

