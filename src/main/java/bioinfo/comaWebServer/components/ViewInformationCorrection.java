package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.InformationCorrection;

public class ViewInformationCorrection 
{
	@Parameter(required = true)
	private InformationCorrection informationCorrection;

	public InformationCorrection getInformationCorrection() {
		return informationCorrection;
	}

	public void setInformationCorrection(InformationCorrection informationCorrection) {
		this.informationCorrection = informationCorrection;
	}
	
	@Parameter(required = true)
	private boolean showInformationCorrection;

	public boolean isShowInformationCorrection() {
		return showInformationCorrection;
	}

	public void setShowInformationCorrection(boolean showInformationCorrection) {
		this.showInformationCorrection = showInformationCorrection;
	}

}
