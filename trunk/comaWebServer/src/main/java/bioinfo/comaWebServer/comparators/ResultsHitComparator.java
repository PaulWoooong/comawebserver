package bioinfo.comaWebServer.comparators;

import java.util.Comparator;

import bioinfo.comaWebServer.entities.ResultsHit;

public class ResultsHitComparator implements Comparator<ResultsHit>
{
	public int compare(ResultsHit a, ResultsHit b) 
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
