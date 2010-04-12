package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlignmentBibliography implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String pdbId;
	private String pfamId;
	private String scopId;
	private String pubmedQuery;
	private String pubmedQueryForHtml;
	
	private Set<ScopBibliography> scopBibliography;
	
	public AlignmentBibliography(){}
	
	public AlignmentBibliography(String pdbId, String scopId, String scopInfo, String pubmedQuery, String pfamId)
	{
		this(pdbId, scopId, scopInfo, pubmedQuery);
		this.pfamId = pfamId;
	}
	
	public AlignmentBibliography(String pdbId, String scopId, String scopInfo, String pubmedQuery)
	{
		this.pdbId = pdbId;
		this.scopId = scopId;
		this.pubmedQuery = pubmedQuery;
		
		Set<ScopBibliography> scopBibliography = null;
		
		if(scopInfo != null)
		{
			Pattern pattern = Pattern.compile("^\\s*$");
			Matcher matcher = pattern.matcher(scopInfo);
			if(matcher.matches())
			{
				scopInfo = null;
			}
			else
			{
				scopInfo = scopInfo.replaceAll("^\\s+", "");
				scopInfo = scopInfo.replaceAll("\\s+$", "");
			}
			
			if(scopInfo != null)
			{	
				String[] info = scopInfo.split("\\s+");
				
				if(info.length > 0 && info.length % 2 == 0)
				{
					scopBibliography = new HashSet<ScopBibliography>();
					
					for(int i = 0; i < info.length; i += 2)
					{
						ScopBibliography sb = new ScopBibliography(info[i], info[i + 1]);
						
						scopBibliography.add(sb);
					}
				}
			}
		}
		
		setScopBibliography(scopBibliography);
	}
	
	public String getTextForImages()
	{
		String text = null;
		
		if(getPdbId() != null)
		{
			text = getPdbId();
			if(getHierarchies() != null)
			{
				text += " (" + getHierarchies() + ")";
			}
		}
		else if(getPfamId() != null)
		{
			text = getPfamId();
		}
		
		return text;
	}
	
	public String getHierarchies()
	{
		String hierarchies = null;
		
		Set<ScopBibliography> scopBibliographies = getScopBibliography();
		
		if(scopBibliography != null)
		{
			int max = 10;
			
			hierarchies = " ";
			int counter = 0;
			
			for(ScopBibliography sb: scopBibliographies)
			{
				if(counter < max)
				{
					hierarchies += sb.getScopHierarchy() + " ";
					counter++;
				}
			}
			if(scopBibliographies.size() > max)
			{
				hierarchies += "...";
			}
			
		}
		return hierarchies;
	}
	
	public List<String> getHierarchyLink()
	{
		List<String> hierarchies = null;
		
		Set<ScopBibliography> scopBibliographies = getScopBibliography();
		
		if(scopBibliography != null && scopBibliographies.size() > 0)
		{	
			hierarchies = new ArrayList<String>();
			
			int counter = 0;
			
			String links = "";
			
			for(ScopBibliography sb: scopBibliographies)
			{
				links += "<a href=\"http://scop.mrc-lmb.cam.ac.uk/scop/search.cgi?key=" 
							+ sb.getScopId() + "\" target=\"_blank\">";
				links += sb.getScopHierarchy();
				links += "</a><br/>";
				
				counter++;
				
				if(counter % 3 == 0)
				{
					hierarchies.add(links);
					links = "";
				}
			}
			
			if(!links.equals(""))
			{
				hierarchies.add(links);
			}
		}
		return hierarchies;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPdbId() {
		return pdbId;
	}
	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}
	public String getScopId() {
		return scopId;
	}
	public void setScopId(String scopId) {
		this.scopId = scopId;
	}
	
	public String getPubmedQueryForHtml() 
	{
		if(pubmedQueryForHtml == null && pubmedQuery != null)
		{
			String noWhitespacesInBegining = pubmedQuery.replaceAll("^\\s+", "");
			String noWhitespacesInBeginingAndEnd = noWhitespacesInBegining.replaceAll("\\s+$", "");
			pubmedQueryForHtml = noWhitespacesInBeginingAndEnd.replaceAll("\\s+", "+AND+");
		}
		return pubmedQueryForHtml;
	}
	
	public String getPubmedQuery() {
		return pubmedQuery;
	}
	public void setPubmedQuery(String pubmedQuery) {
		this.pubmedQuery = pubmedQuery;
	}

	public Set<ScopBibliography> getScopBibliography() {
		return scopBibliography;
	}

	public void setScopBibliography(Set<ScopBibliography> scopBibliography) {
		this.scopBibliography = scopBibliography;
	}

	public String getPfamId() {
		return pfamId;
	}

	public void setPfamId(String pfamId) {
		this.pfamId = pfamId;
	}
}
