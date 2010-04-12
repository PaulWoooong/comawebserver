package bioinfo.comaWebServer.entities;


/*
## High-scoring segment pairs:
###
#   Length of HSP hit                                   [3-50]
HSPLEN = 3
#   Minimum score of HSP hits                           [Integer]
#    0 -- disables heuristics of HSPs
HSPMINSCORE = 7
#   Maximum distance between terminal HSP hits          [Integer]
HSPMAXDIST = 60
#   Number of HSPs used in multiple hits heuristics     [1-10]
NOHSPS = 3
 */
public class HSSegmentPairs extends AbstractParameter 
{
	private long id;
	
	private int hsplen = 3;
	private int hsminscore = 7;
	private int hsmaxdist = 60;
	private int nohsps = 3;

	public String getValues() 
	{
		StringBuffer buffer = new StringBuffer("## High-scoring segment pairs:\n");
		buffer.append("###\n");
		buffer.append("#   Length of HSP hit                                   [3-50]\n");
		buffer.append("HSPLEN=" + hsplen + "\n");
		buffer.append("#   Minimum score of HSP hits  0 -- disables heuristics of HSPs\n");
		buffer.append("HSPMINSCORE=" + hsminscore + "\n");
		buffer.append("#   Maximum distance between terminal HSP hits\n");
		buffer.append("HSPMAXDIST=" + hsmaxdist + "\n");
		buffer.append("#   Number of HSPs used in multiple hits heuristics\n");
		buffer.append("NOHSPS=" + nohsps + "\n");
		return buffer.toString();
	}

	public int getHsplen() {
		return hsplen;
	}

	public void setHsplen(int hsplen) {
		this.hsplen = hsplen;
	}

	public int getHsminscore() {
		return hsminscore;
	}

	public void setHsminscore(int hsminscore) {
		this.hsminscore = hsminscore;
	}

	public int getHsmaxdist() {
		return hsmaxdist;
	}

	public void setHsmaxdist(int hsmaxdist) {
		this.hsmaxdist = hsmaxdist;
	}

	public int getNohsps() {
		return nohsps;
	}

	public void setNohsps(int nohsps) {
		this.nohsps = nohsps;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
