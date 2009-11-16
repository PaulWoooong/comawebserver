package bioinfo.comaWebServer.comparators;

import java.util.Comparator;

import bioinfo.comaWebServer.entities.ResultsAlignment;

public class ResultsAlignmentComparator implements Comparator<ResultsAlignment> 
{
	public int compare(ResultsAlignment a, ResultsAlignment b) 
	{
		if(a.getPriority() > b.getPriority())
		{
			return 1;
		}
		if(a.getPriority() < b.getPriority())
		{
			return -1;
		}
		return 0;
	}

}
