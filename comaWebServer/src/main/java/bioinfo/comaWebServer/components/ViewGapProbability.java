package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.GapProbability;

public class ViewGapProbability 
{
	@Parameter(required=true)
	private GapProbability gapProbability;
	
	@Parameter(required=true)
	private boolean showGapProbability;

	public GapProbability getGapProbability() {
		return gapProbability;
	}

	public void setGapProbability(GapProbability gapProbability) {
		this.gapProbability = gapProbability;
	}

	public boolean isShowGapProbability() {
		return showGapProbability;
	}

	public void setShowGapProbability(boolean showGapProbability) {
		this.showGapProbability = showGapProbability;
	}

}
