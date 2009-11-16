package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.AlignmentFilter;

public class ViewAlignmentFilter 
{
	@Parameter(required = true)
	private AlignmentFilter alignmentFilter;
	
	@Parameter(required = true)
	private boolean showAlignmentFilter;

	public AlignmentFilter getAlignmentFilter() {
		return alignmentFilter;
	}

	public void setAlignmentFilter(AlignmentFilter alignmentFilter) {
		this.alignmentFilter = alignmentFilter;
	}

	public boolean isShowAlignmentFilter() {
		return showAlignmentFilter;
	}

	public void setShowAlignmentFilter(boolean showAlignmentFilter) {
		this.showAlignmentFilter = showAlignmentFilter;
	}
}
