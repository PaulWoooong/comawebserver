package bioinfo.comaWebServer.dataServices;

import java.io.IOException;
import java.util.Map;

import bioinfo.comaWebServer.entities.AlignmentBibliography;
import bioinfo.comaWebServer.entities.ComaResults;

public interface IParser 
{
	public void parse(ComaResults comaResults, String path) throws IOException;
	public Map<String, AlignmentBibliography> parseIDS(String path) throws IOException;
	public int numberOfSeq(String path) throws IOException;
}
