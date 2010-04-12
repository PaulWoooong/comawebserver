package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.SEG;

public class ViewSEG 
{
	@Parameter(required = true)
	private SEG seg;
	
	@Parameter(required = true)
	private boolean showSEG;

	public SEG getSeg() {
		return seg;
	}

	public void setSeg(SEG seg) {
		this.seg = seg;
	}

	public boolean isShowSEG() {
		return showSEG;
	}

	public void setShowSEG(boolean showSEG) {
		this.showSEG = showSEG;
	}

}
