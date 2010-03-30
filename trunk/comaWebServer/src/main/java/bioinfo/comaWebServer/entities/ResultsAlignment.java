package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bioinfo.comaWebServer.comparators.ResultsQueryComparator;

public class ResultsAlignment implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private long comaResultsId;
	private int priority;
	private String name;
	
	private String header;
	private String queryLength;
	private String subjectLength;
	private String score;
	private String expect;
	private String pvalue;
	private String identities;
	private String positives;
	private String gaps;
	
	private String nonNegativeScore;
	
	private String ungappedK;
	private String ungappedLambda;
	private String gappedK;
	private String gappedLambda;
	private String entropy;
	private String expected;
	private String minMax;
	
	private AlignmentBibliography alignmentBibliography;
	
	private Set<ResultsQuery> queries;

	private boolean hasHeader;
	private boolean hasFooter;
	
	public String getFilteredHeader() 
	{
		if(header != null)
		{
			Pattern p = Pattern.compile(".*(PF\\d+)\\.\\d+.*");
			Matcher m = p.matcher(header);
			if(m.matches())
			{
				String pfamID = m.group(1);
				return header.replaceFirst("PF\\d+\\.\\d+", pfamID);
			}
		}
		return header;
	}
	
/*	>1dsr_A mol:protein length:17  RAMOPLANIN
	  Query length = 4, Sbjct length = 17

	 Score = 378.78 (83.1 bits),  Expect = 1.336e-18, P-value = 0
	 Identities = 9/69 (13%), Positives = 24/69 (34%), Gaps = 17/69 (24%)

	Query:     1 TEST 4
	             T  T
	Sbjct:     5 TXGT 8

	                           K        Lambda
	Computed  ungapped,        0.4187   0.2597
	Estimated gapped,          0.0164   0.1412
	Entropy, 2.0693; Expected, -78.1867; Min/Max, -135/8*/
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(header + "\n");
		buffer.append("  Query length = " + queryLength + ", Sbjct length = " + subjectLength + "\n\n");
		buffer.append(" Score = " + score + ", Expect = " + expect + ", P-value = " + pvalue + "\n");
		
		if(identities != null)
		{
			buffer.append(" Identities = " + identities + ", ");
		}
		if(positives != null)
		{
			buffer.append(" Positives = " + positives + ", ");
		}
		if(gaps != null)
		{
			buffer.append(" Gaps = " + gaps);
		}
		buffer.append("\n\n");
		
		ArrayList<ResultsQuery> sortedList = new ArrayList<ResultsQuery>(queries);
		Collections.sort(sortedList, new ResultsQueryComparator());
		
		for(ResultsQuery query: sortedList)
		{
			buffer.append(query.toString());
		}
		
		buffer.append(String.format("%1$30s %2$10s %3$10s\n", "", "K", "Lambda"));
		buffer.append(String.format("%1$30s %2$10s %3$10s\n", "Computed  ungapped,", ungappedK, ungappedLambda));
		buffer.append(String.format("%1$30s %2$10s %3$10s\n", "Estimated gapped,", gappedK, gappedLambda));
		
		if(entropy != null)
		{
			buffer.append("Entropy, " + entropy + "; ");
		}
		if(expected != null)
		{
			buffer.append("Expected, " + expected + "; ");
		}
		if(minMax != null)
		{
			buffer.append("Min/Max, " + minMax + ";\n");
		}
		
		return buffer.toString();
	}
	
	
	public void setRheader(RAlignmentHeader rheader)
	{
		if(rheader != null)
		{
			hasHeader		= true;
			
			header 			= rheader.getHeader();
			
			int end = 31;
			if(end > header.length())
			{
				end = header.length();
			}
			name = header.substring(1, end).replaceAll("\\s+$", "");
			
			queryLength 	= rheader.getQueryLength();
			subjectLength	= rheader.getSubjectLength();
			score			= rheader.getScore();
			expect			= rheader.getExpect();
			pvalue			= rheader.getPvalue();
			identities		= rheader.getIdentities();
			positives		= rheader.getPositives();
			gaps			= rheader.getGaps();
		}
		else
		{
			hasHeader		= false;
		}
		
	}
	
	public void setRfooter(RAlignmentFooter rfooter)
	{
		if(rfooter != null)
		{
			hasFooter		= true;
			
			ungappedK		= rfooter.getUngappedK();
			ungappedLambda	= rfooter.getUngappedLambda();
			gappedK			= rfooter.getGappedK();
			gappedLambda	= rfooter.getGappedLambda();
			entropy			= rfooter.getEntropy();
			expected		= rfooter.getExpected();
			minMax			= rfooter.getMinMax();
		}
		else
		{
			hasFooter		= false;
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public String getEntropy() {
		return entropy;
	}
	public void setEntropy(String entropy) {
		this.entropy = entropy;
	}
	public String getExpected() {
		return expected;
	}
	public void setExpected(String expected) {
		this.expected = expected;
	}
	public String getMinMax() {
		return minMax;
	}
	public void setMinMax(String minMax) {
		this.minMax = minMax;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	public Set<ResultsQuery> getQueries() {
		return queries;
	}

	public void setQueries(Set<ResultsQuery> queries) {
		this.queries = queries;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public boolean isHasFooter() {
		return hasFooter;
	}

	public void setHasFooter(boolean hasFooter) {
		this.hasFooter = hasFooter;
	}

	public String getNonNegativeScore() {
		return nonNegativeScore;
	}

	public void setNonNegativeScore(String nonNegativeScore) {
		this.nonNegativeScore = nonNegativeScore;
	}

	public void setAlignmentBibliography(AlignmentBibliography alignmentBibliography) {
		this.alignmentBibliography = alignmentBibliography;
	}

	public AlignmentBibliography getAlignmentBibliography() {
		return alignmentBibliography;
	}


	public long getComaResultsId() {
		return comaResultsId;
	}


	public void setComaResultsId(long comaResultsId) {
		this.comaResultsId = comaResultsId;
	}

}
