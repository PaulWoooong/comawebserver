package bioinfo.comaWebServer.entities;

import java.io.Serializable;

public class ResultsFooter  implements Serializable
{	
	private static final long serialVersionUID = 1L;

	private long id;
	private String scoringMethod;
	private String gapOpenCost;
	private String initialGapExtensionCost;
	private String deletionProbabilityWeight;
	private String ungappedK;
	private String ungappedLambda;
	private String gappedK;
	private String gappedLambda;
	private String queryLength;
	private String databaseLength;
	private String queryEffectiveLength;
	private String databaseEffectiveLength;
	private String effectiveSearchSpace;

	public String getScoringMethod() {
		return scoringMethod;
	}
	public void setScoringMethod(String scoringMethod) {
		this.scoringMethod = scoringMethod;
	}
	public String getGapOpenCost() {
		return gapOpenCost;
	}
	public void setGapOpenCost(String gapOpenCost) {
		this.gapOpenCost = gapOpenCost;
	}
	public String getInitialGapExtensionCost() {
		return initialGapExtensionCost;
	}
	public void setInitialGapExtensionCost(String initialGapExtensionCost) {
		this.initialGapExtensionCost = initialGapExtensionCost;
	}
	public String getDeletionProbabilityWeight() {
		return deletionProbabilityWeight;
	}
	public void setDeletionProbabilityWeight(String deletionProbabilityWeight) {
		this.deletionProbabilityWeight = deletionProbabilityWeight;
	}
	public String getUngappedK() {
		return ungappedK;
	}
	public void setUngappedK(String ungappedK) {
		this.ungappedK = ungappedK;
	}
	public String getUngappedLambda() {
		return ungappedLambda;
	}
	public void setUngappedLambda(String ungappedLambda) {
		this.ungappedLambda = ungappedLambda;
	}
	public String getGappedK() {
		return gappedK;
	}
	public void setGappedK(String gappedK) {
		this.gappedK = gappedK;
	}
	public String getGappedLambda() {
		return gappedLambda;
	}
	public void setGappedLambda(String gappedLambda) {
		this.gappedLambda = gappedLambda;
	}
	public String getQueryLength() {
		return queryLength;
	}
	public void setQueryLength(String queryLength) {
		this.queryLength = queryLength;
	}
	public String getDatabaseLength() {
		return databaseLength;
	}
	public void setDatabaseLength(String databaseLength) {
		this.databaseLength = databaseLength;
	}
	public String getQueryEffectiveLength() {
		return queryEffectiveLength;
	}
	public void setQueryEffectiveLength(String queryEffectiveLength) {
		this.queryEffectiveLength = queryEffectiveLength;
	}
	public String getDatabaseEffectiveLength() {
		return databaseEffectiveLength;
	}
	public void setDatabaseEffectiveLength(String databaseEffectiveLength) {
		this.databaseEffectiveLength = databaseEffectiveLength;
	}
	public String getEffectiveSearchSpace() {
		return effectiveSearchSpace;
	}
	public void setEffectiveSearchSpace(String effectiveSearchSpace) {
		this.effectiveSearchSpace = effectiveSearchSpace;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
