package bioinfo.comaWebServer.entities;

import java.util.ArrayList;
import java.util.List;

public class ComaParams 
{
	private Autocorrection autocorrection = null;
	private Alignment alignment = null;
	private Output output = null;
	private ProfileConstruction profileConstruction = null;
	private Masking masking = null;
	private SEG seg = null;
	private GapProbability gapProbability = null;
	private InformationCorrection informationCorrection = null;
	private AlignmentFilter alignmentFilter = null;
	private HSSegmentPairs segmentPairs = null;
	
	private List<AbstractParameter> params = null;
	 
	public Autocorrection getAutocorrection() {
		return autocorrection;
	}
	public void setAutocorrection(Autocorrection autocorrection) {
		this.autocorrection = autocorrection;
	}
	public Alignment getAlignment() {
		return alignment;
	}
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	public Output getOutput() {
		return output;
	}
	public void setOutput(Output output) {
		this.output = output;
	}
	public ProfileConstruction getProfileConstruction() {
		return profileConstruction;
	}
	public void setProfileConstruction(ProfileConstruction profileConstruction) {
		this.profileConstruction = profileConstruction;
	}
	public Masking getMasking() {
		return masking;
	}
	public void setMasking(Masking masking) {
		this.masking = masking;
	}
	public SEG getSeg() {
		return seg;
	}
	public void setSeg(SEG seg) {
		this.seg = seg;
	}
	public GapProbability getGapProbability() {
		return gapProbability;
	}
	public void setGapProbability(GapProbability gapProbability) {
		this.gapProbability = gapProbability;
	}
	public InformationCorrection getInformationCorrection() {
		return informationCorrection;
	}
	public void setInformationCorrection(InformationCorrection informationCorrection) {
		this.informationCorrection = informationCorrection;
	}
	public AlignmentFilter getAlignmentFilter() {
		return alignmentFilter;
	}
	public void setAlignmentFilter(AlignmentFilter alignmentFilter) {
		this.alignmentFilter = alignmentFilter;
	}
	public HSSegmentPairs getSegmentPairs() {
		return segmentPairs;
	}
	public void setSegmentPairs(HSSegmentPairs segmentPairs) {
		this.segmentPairs = segmentPairs;
	}

	public List<AbstractParameter> getParams() 
	{
		params = new ArrayList<AbstractParameter>();
		
		params.add(output);
		params.add(alignment);
		params.add(profileConstruction);
		params.add(alignmentFilter);
		params.add(masking);
		params.add(seg);
		params.add(gapProbability);
		params.add(autocorrection);
		params.add(informationCorrection);
		params.add(segmentPairs);
		
		return params;
	}

}
