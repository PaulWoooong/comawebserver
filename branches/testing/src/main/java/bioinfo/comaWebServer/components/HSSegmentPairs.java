package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

public class HSSegmentPairs 
{
	@Parameter(required=true)
	private bioinfo.comaWebServer.entities.HSSegmentPairs segmentPairs;

	public bioinfo.comaWebServer.entities.HSSegmentPairs getSegmentPairs() {
		return segmentPairs;
	}

	public void setSegmentPairs(
			bioinfo.comaWebServer.entities.HSSegmentPairs segmentPairs) {
		this.segmentPairs = segmentPairs;
	}
}
