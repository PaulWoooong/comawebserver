package bioinfo.comaWebServer.entities;

import java.io.Serializable;


public class ResultsQuery implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private long resultsAlignmentId;
	private int priority;
	private String query;
	private String queryBegin;
	private String queryEnd;
	private String subject;
	private String subjectBegin;
	private String subjectEnd;
	private String info;
	
/*	Query:     1 TEST 4
    		     T  T
    Sbjct:     5 TXGT 8*/
	
	public String toString()
	{

		String queryInfo = String.format("%1$6s %2$10s %3$100s %4$10s\n", "Query:", queryBegin, query, queryEnd);
		String justInfo = String.format("%1$6s %2$10s %3$100s %4$10s\n", "", "", info, "");
		String sbjctInfo = String.format("%1$6s %2$10s %3$100s %4$10s\n\n", "Sbjct:", subjectBegin, subject, subjectEnd);
		
		return queryInfo + justInfo + sbjctInfo;
	}
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	public long getResultsAlignmentId() {
		return resultsAlignmentId;
	}
	public void setResultsAlignmentId(long resultsAlignmentId) {
		this.resultsAlignmentId = resultsAlignmentId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQueryBegin() {
		return queryBegin;
	}
	public void setQueryBegin(String queryBegin) {
		this.queryBegin = queryBegin;
	}
	public String getQueryEnd() {
		return queryEnd;
	}
	public void setQueryEnd(String queryEnd) {
		this.queryEnd = queryEnd;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubjectBegin() {
		return subjectBegin;
	}
	public void setSubjectBegin(String subjectBegin) {
		this.subjectBegin = subjectBegin;
	}
	public String getSubjectEnd() {
		return subjectEnd;
	}
	public void setSubjectEnd(String subjectEnd) {
		this.subjectEnd = subjectEnd;
	}
	public String getInfoHtml() 
	{
		return info.replaceAll("\\s", "&nbsp;");
	}
	public String getInfo() 
	{
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
