package bioinfo.comaWebServer.enums;

public enum PsiBlastFilters 
{
	NO_FILTERING   ("No filtering"),
	LOW_COMPLEXITY ("Low complexity"),
	MASK  		   ("Mask for lookup table only");
	
	private final String filters;
	
	PsiBlastFilters(String filters)
	{
		this.filters = filters;
	}

	public String getFilters() {
		return filters;
	}
}
