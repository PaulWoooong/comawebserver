package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.ResultsQuery;

public class ViewResultsQuery 
{
	@Parameter(required = true)
	private ResultsQuery resultsQuery;

	public ResultsQuery getResultsQuery() {
		return resultsQuery;
	}

	public void setResultsQuery(ResultsQuery resultsQuery) {
		this.resultsQuery = resultsQuery;
	}
}
