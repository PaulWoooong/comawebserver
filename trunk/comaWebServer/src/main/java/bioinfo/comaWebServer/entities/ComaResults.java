package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.Set;

public class ComaResults  implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String noProfilesFound;
	
	private String profileDBName;
	private String profileDBValue;
	private Boolean supportModeling;
	
	private String sequenceDBName;
	private String sequenceDBValue;
	
	private Set<ResultsHit> hits;
	private Set<ResultsAlignment> alignments;
	private Set<Image> images;
	
	private ResultsFooter resultsFooter;
	
	private Search search;
	
	private int numberOfSequences;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Set<ResultsHit> getHits() {
		return hits;
	}
	public void setHits(Set<ResultsHit> hits) {
		this.hits = hits;
	}
	public Set<ResultsAlignment> getAlignments() {
		return alignments;
	}
	public void setAlignments(Set<ResultsAlignment> alignments) {
		this.alignments = alignments;
	}
	public String getNoProfilesFound() {
		return noProfilesFound;
	}
	public void setNoProfilesFound(String noProfilesFound) {
		this.noProfilesFound = noProfilesFound;
	}
	public ResultsFooter getResultsFooter() {
		return resultsFooter;
	}
	public void setResultsFooter(ResultsFooter resultsFooter) {
		this.resultsFooter = resultsFooter;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public String getProfileDBName() {
		return profileDBName;
	}
	public void setProfileDBName(String profileDBName) {
		this.profileDBName = profileDBName;
	}
	public String getProfileDBValue() {
		return profileDBValue;
	}
	public void setProfileDBValue(String profileDBValue) {
		this.profileDBValue = profileDBValue;
	}
	public String getSequenceDBName() {
		return sequenceDBName;
	}
	public void setSequenceDBName(String sequenceDBName) {
		this.sequenceDBName = sequenceDBName;
	}
	public String getSequenceDBValue() {
		return sequenceDBValue;
	}
	public void setSequenceDBValue(String sequenceDBValue) {
		this.sequenceDBValue = sequenceDBValue;
	}
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	public Boolean getSupportModeling() {
		return supportModeling;
	}
	public void setSupportModeling(Boolean supportModeling) {
		this.supportModeling = supportModeling;
	}
	public int getNumberOfSequences() {
		return numberOfSequences;
	}
	public void setNumberOfSequences(int numberOfSequences) {
		this.numberOfSequences = numberOfSequences;
	}
}
