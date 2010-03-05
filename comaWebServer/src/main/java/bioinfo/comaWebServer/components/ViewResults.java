package bioinfo.comaWebServer.components;

import java.util.List;
import java.util.Set;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.entities.Image;
import bioinfo.comaWebServer.entities.Job;
import bioinfo.comaWebServer.entities.ResultsAlignment;
import bioinfo.comaWebServer.entities.ResultsHit;
import bioinfo.comaWebServer.enums.Extentions;
import bioinfo.comaWebServer.enums.PsiBlastRadioParams;

@IncludeStylesheet("context:assets/styles.css")
public class ViewResults 
{
	@Inject
	private Request request;
	
	@Parameter(required = true)
	private Job job;
	
	@Parameter(required = true)
	private List<ResultsHit> hits;
	
	@Parameter(required = true)
	private ResultsHit resultsHit;
	
	@Parameter(required = true)
	private Set<Integer> selectedHits;
	
	private boolean selectAll = false;
	
	private ResultsAlignment resultsAlignment;
	
	private Image image;
	
	public String getComaInpExt()
	{
		return Extentions.INPUT_COMA.getExtention().replaceAll("^\\.", "");
	}
	
	public String getParamsExt()
	{
		return Extentions.PARAMS.getExtention().replaceAll("^\\.", "");
	}
	
	public Job getJob() 
	{
		return job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}

	public ResultsAlignment getResultsAlignment() {
		return resultsAlignment;
	}

	public void setResultsAlignment(ResultsAlignment resultsAlignment) {
		this.resultsAlignment = resultsAlignment;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isSelected() 
	{  
        return getSelectedHits().contains(getResultsHit().getPriority());  
    } 
	
	/** 
     * Add priority to the selected set if selected. 
     */  
    public void setSelected(boolean selected) 
    {  
        if(selected) 
        {  
            getSelectedHits().add(getResultsHit().getPriority());  
        } 
        else 
        {  
        	getSelectedHits().remove(getResultsHit().getPriority());  
        }  
    }
	
	public List<ResultsHit> getHits() {
		return hits;
	}

	public void setHits(List<ResultsHit> hits) {
		this.hits = hits;
	}

	public ResultsHit getResultsHit() {
		return resultsHit;
	}

	public void setResultsHit(ResultsHit resultsHit) {
		this.resultsHit = resultsHit;
	}

	public Set<Integer> getSelectedHits() {
		return selectedHits;
	}

	public void setSelectedHits(Set<Integer> selectedHits) {
		this.selectedHits = selectedHits;
	}
	
	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}  
	
	public boolean getNotRestart()
	{
		if(job.getComaResults().getSearch().getMsaStrategy() == PsiBlastRadioParams.NR)
		{
			return true;
		}
		return false;
	}
	
	public boolean getRestart()
	{
		if(job.getComaResults().getSearch().getMsaStrategy() == PsiBlastRadioParams.RR)
		{
			return true;
		}
		return false;
	}
	
	public boolean getServerHandle()
	{
		if(job.getComaResults().getSearch().getMsaStrategy() == PsiBlastRadioParams.IN)
		{
			return true;
		}
		return false;
	}
	
	public String getMaPath()
	{
		return resultsPath() + job.getGeneratedId() + Extentions.OUTPUT_COMA_MA.getExtention();
	}
	
	public String getFaPath()
	{
		return resultsPath() + job.getGeneratedId() + "." + getComaInpExt();
	}
	
	public String getParamsPath()
	{
		return resultsPath() + job.getGeneratedId() + "." + getParamsExt();
	}
	
	public String resultsPath()
	{
		if(Cache.getClusterParams().getLocalFilePath() == null)
		{
			return "";
		}
		return request.getContextPath() +  
				Cache.getClusterParams().getLocalFilePath() + 
				job.getGeneratedId() + "/";
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
