package bioinfo.comaWebServer.entities;

public class RAlignmentHeader 
{
	private long id;
	private String header;
	private String queryLength;
	private String subjectLength;
	private String score;
	private String expect;
	private String pvalue;
	private String identities;
	private String positives;
	private String gaps;
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getQueryLength() {
		return queryLength;
	}
	public void setQueryLength(String queryLength) {
		this.queryLength = queryLength;
	}
	public String getSubjectLength() {
		return subjectLength;
	}
	public void setSubjectLength(String subjectLength) {
		this.subjectLength = subjectLength;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getIdentities() {
		return identities;
	}
	public void setIdentities(String identities) {
		this.identities = identities;
	}
	public String getPositives() {
		return positives;
	}
	public void setPositives(String positives) {
		this.positives = positives;
	}
	public String getGaps() {
		return gaps;
	}
	public void setGaps(String gaps) {
		this.gaps = gaps;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
