package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;

import bioinfo.comaWebServer.entities.Alignment;
import bioinfo.comaWebServer.entities.AlignmentFilter;
import bioinfo.comaWebServer.entities.Autocorrection;
import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.entities.GapProbability;
import bioinfo.comaWebServer.entities.HSSegmentPairs;
import bioinfo.comaWebServer.entities.InformationCorrection;
import bioinfo.comaWebServer.entities.Masking;
import bioinfo.comaWebServer.entities.Output;
import bioinfo.comaWebServer.entities.ProfileConstruction;
import bioinfo.comaWebServer.entities.SEG;

public class ViewComaParams 
{
	@Parameter(required=true)
	private List<DatabaseItem> alignmentDB;

	public List<DatabaseItem> getAlignmentDB() {
		return alignmentDB;
	}

	public void setAlignmentDB(List<DatabaseItem> alignmentDB) {
		this.alignmentDB = alignmentDB;
	}

	@Persist
	private boolean showAdvanced;
	@OnEvent(component="showAdvancedButton", value = "selected")
	void onShowAdvancedButton()
	{
		showAdvanced = true;
		
		showAdvanced = true;
		showAutocorrection = true;
		showAlignment = true;
		showGapProbability = true;
		showInformationCorrection = true;
		showMasking = true;
		showOutput = true;
		showSEG = true;
		showAlignmentFilter = true;
		showProfileConstruction = true;
	}
	
	@OnEvent(component="hideAdvancedButton", value = "selected")
	void onHideAdvancedButton()
	{
		showAdvanced = false;
	}
	
	@Parameter(required = true)
	private AlignmentFilter alignmentFilter;
	@Persist
	private boolean showAlignmentFilter;
	
	@OnEvent(component="showAlignmentFilterButton", value = "selected")
	void onShowAlignmentFilterButton()
	{
		showAlignmentFilter = true;
	}
	
	@OnEvent(component="hideAlignmentFilterButton", value = "selected")
	void onHideAlignmentFilterButton()
	{
		showAlignmentFilter = false;
	}
	
	@Parameter(required = true)
	private Autocorrection autocorrection;
	@Persist
	private boolean showAutocorrection;
	
	@OnEvent(component="showAutocorrectionButton", value = "selected")
	void onShowAutocorrectionButton()
	{
		showAutocorrection = true;
	}
	
	@OnEvent(component="hideAutocorrectionButton", value = "selected")
	void onHideAutocorrectionButton()
	{
		showAutocorrection = false;
	}
	
	@Parameter(required = true)
	private Alignment alignment;
	@Persist
	private boolean showAlignment;
	
	@OnEvent(component="showAlignmentButton", value = "selected")
	void onShowAlignmentButton()
	{
		showAlignment = true;
	}
	
	@OnEvent(component="hideAlignmentButton", value = "selected")
	void onHideAlignmentButton()
	{
		showAlignment = false;
	}
	
	@Parameter(required = true)
	private GapProbability gapProbability;
	@Persist
	private boolean showGapProbability;
	
	@OnEvent(component="showGapProbabilityButton", value = "selected")
	void onShowGapProbabilityButton()
	{
		showGapProbability = true;
	}
	
	@OnEvent(component="hideGapProbabilityButton", value = "selected")
	void onHideGapProbabilityButton()
	{
		showGapProbability = false;
	}
	
	@Parameter(required = true)
	private InformationCorrection informationCorrection;
	@Persist
	private boolean showInformationCorrection;
	
	@OnEvent(component="showInformationCorrectionButton", value = "selected")
	void onShowInformationCorrectionButton()
	{
		showInformationCorrection = true;
	}
	
	@OnEvent(component="hideInformationCorrectionButton", value = "selected")
	void onHideInformationCorrectionButton()
	{
		showInformationCorrection = false;
	}
	
	@Parameter(required = true)
	private Masking masking;
	@Persist
	private boolean showMasking;
	
	@OnEvent(component="showMaskingButton", value = "selected")
	void onShowMaskingButton()
	{
		showMasking = true;
	}
	
	@OnEvent(component="hideMaskingButton", value = "selected")
	void onHideMaskingButton()
	{
		showMasking = false;
	}
	
	@Parameter(required = true)
	private Output output;
	@Persist
	private boolean showOutput;
	
	@OnEvent(component="showOutputButton", value = "selected")
	void onShowOutputButton()
	{
		showOutput = true;
	}
	
	@OnEvent(component="hideOutputButton", value = "selected")
	void onHideOutputButton()
	{
		showOutput = false;
	}
	
	@Parameter(required = true)
	private ProfileConstruction profileConstruction;
	@Persist
	private boolean showProfileConstruction;
	
	@OnEvent(component="showProfileConstructionButton", value = "selected")
	void onShowProfileConstructionButton()
	{
		showProfileConstruction = true;
	}
	
	@OnEvent(component="hideProfileConstructionButton", value = "selected")
	void onHideProfileConstructionButton()
	{
		showProfileConstruction = false;
	}
	
	@Parameter(required = true)
	private SEG seg;
	@Persist
	private boolean showSEG;
	
	@OnEvent(component="showSEGButton", value = "selected")
	void onShowSEGButton()
	{
		showSEG = true;
	}
	
	@OnEvent(component="hideSEGButton", value = "selected")
	void onHideSEGButton()
	{
		showSEG = false;
	}
	
	@Parameter(required = true)
	private HSSegmentPairs segmentPairs;

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

	public Masking getMasking() {
		return masking;
	}

	public void setMasking(Masking masking) {
		this.masking = masking;
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

	public SEG getSeg() {
		return seg;
	}

	public void setSeg(SEG seg) {
		this.seg = seg;
	}

	public boolean isShowAutocorrection() {
		return showAutocorrection;
	}

	public void setShowAutocorrection(boolean showAutocorrection) {
		this.showAutocorrection = showAutocorrection;
	}

	public boolean isShowAlignment() {
		return showAlignment;
	}

	public void setShowAlignment(boolean showAlignment) {
		this.showAlignment = showAlignment;
	}

	public boolean isShowGapProbability() {
		return showGapProbability;
	}

	public void setShowGapProbability(boolean showGapProbability) {
		this.showGapProbability = showGapProbability;
	}

	public boolean isShowInformationCorrection() {
		return showInformationCorrection;
	}

	public void setShowInformationCorrection(boolean showInformationCorrection) {
		this.showInformationCorrection = showInformationCorrection;
	}

	public boolean isShowMasking() {
		return showMasking;
	}

	public void setShowMasking(boolean showMasking) {
		this.showMasking = showMasking;
	}

	public boolean isShowOutput() {
		return showOutput;
	}

	public void setShowOutput(boolean showOutput) {
		this.showOutput = showOutput;
	}

	public boolean isShowProfileConstruction() {
		return showProfileConstruction;
	}

	public void setShowProfileConstruction(boolean showProfileConstruction) {
		this.showProfileConstruction = showProfileConstruction;
	}

	public boolean isShowSEG() {
		return showSEG;
	}

	public void setShowSEG(boolean showSEG) {
		this.showSEG = showSEG;
	}

	public boolean isShowAdvanced() {
		return showAdvanced;
	}

	public void setShowAdvanced(boolean showAdvanced) {
		this.showAdvanced = showAdvanced;
	}

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

	public void setSegmentPairs(HSSegmentPairs segmentPairs) {
		this.segmentPairs = segmentPairs;
	}

	public HSSegmentPairs getSegmentPairs() {
		return segmentPairs;
	}
}
