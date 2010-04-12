package bioinfo.comaWebServer.entities;

public class ResultsHeader 
{
	private long jobId;
	private String comaVersion;
	private String queryPositions;
	private String target;
	private String fastaHeader;
	private String dbName;
	private String dbProfiles;
	private String dbTotalPositions;
	
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	public String getComaVersion() {
		return comaVersion;
	}
	public void setComaVersion(String comaVersion) {
		this.comaVersion = comaVersion;
	}
	public String getQueryPositions() {
		return queryPositions;
	}
	public void setQueryPositions(String queryPositions) {
		this.queryPositions = queryPositions;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getFastaHeader() {
		return fastaHeader;
	}
	public void setFastaHeader(String biologicalTarget) {
		this.fastaHeader = biologicalTarget;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbProfiles() {
		return dbProfiles;
	}
	public void setDbProfiles(String dbProfiles) {
		this.dbProfiles = dbProfiles;
	}
	public String getDbTotalPositions() {
		return dbTotalPositions;
	}
	public void setDbTotalPositions(String dbTotalPositions) {
		this.dbTotalPositions = dbTotalPositions;
	}
}
