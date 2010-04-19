package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.Autocorrection;

public class ViewAutocorrection 
{
	@Parameter(required = true)
	private Autocorrection autocorrection;

	public Autocorrection getAutocorrection() {
		return autocorrection;
	}

	public void setAutocorrection(Autocorrection autocorrection) {
		this.autocorrection = autocorrection;
	}
	
	@Parameter(required = true)
	private boolean showAutocorrection;

	public boolean isShowAutocorrection() {
		return showAutocorrection;
	}

	public void setShowAutocorrection(boolean showAutocorrection) {
		this.showAutocorrection = showAutocorrection;
	}
}
