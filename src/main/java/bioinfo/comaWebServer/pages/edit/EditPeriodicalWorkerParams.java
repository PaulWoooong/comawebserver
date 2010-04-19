package bioinfo.comaWebServer.pages.edit;

import org.acegisecurity.annotation.Secured;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import bioinfo.comaWebServer.cache.Cache;
import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.entities.PeriodicalWorkerParams;
import bioinfo.comaWebServer.pages.show.ShowInfo;

@Secured("ROLE_ADMIN")
public class EditPeriodicalWorkerParams 
{
	@Inject
	private IDataSource dataSource;
	
	private PeriodicalWorkerParams periodicalWorker;
	
	public void onActivate()
	{
		periodicalWorker = Cache.getPeriodicalWorkerParams();
	}
	
	@InjectPage
	private ShowInfo infoPage;
	
	@OnEvent(component="UpdatePeriodicalWorkerParamsForm", value = "submit")
	@Secured("ROLE_ADMIN")
	Object onUpdatePeriodicalWorkerParamsForm()
	{
		String info = dataSource.updateParams(periodicalWorker);
		Cache.refreshPeriodicalWorkerParams();
		
		infoPage.setUp(info, "Updating cluster params:");
		
		return infoPage;
	}

	public PeriodicalWorkerParams getPeriodicalWorker() 
	{
		if(periodicalWorker == null)
		{
			periodicalWorker = new PeriodicalWorkerParams();
		}
		
		return periodicalWorker;
	}

	public void setPeriodicalWorker(
			PeriodicalWorkerParams periodicalWorkerParams) {
		this.periodicalWorker = periodicalWorkerParams;
	}
}
