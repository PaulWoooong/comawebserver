package bioinfo.comaWebServer.comparators;

import java.util.Comparator;

import bioinfo.comaWebServer.entities.ResultsQuery;

public class ResultsQueryComparator  implements Comparator<ResultsQuery>
{
	public int compare(ResultsQuery a, ResultsQuery b) 
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

