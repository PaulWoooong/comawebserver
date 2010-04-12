package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.Masking;

public class ViewMasking 
{
	@Parameter(required = true)
	private Masking masking;

	public Masking getMasking() {
		return masking;
	}

	public void setMasking(Masking masking) {
		this.masking = masking;
	}

	@Parameter(required = true)
	private boolean showMasking;

	public boolean isShowMasking() {
		return showMasking;
	}

	public void setShowMasking(boolean showMasking) {
		this.showMasking = showMasking;
	}

}
