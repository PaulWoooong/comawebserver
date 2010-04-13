package bioinfo.comaWebServer.entities;

/*
## Output options:
##
#   Print hits with e-value below this value            [Real]
EVAL = 10.0
#   Number of hits to show in the result list           [Integer]
NOHITS = 500
#   Number of alignments to show in the output          [Integer]
NOALNS = 500
#   Show statistical parameters below alignments        (0|1)
SHOW = 1
*/

public class Output extends AbstractParameter
{
	private long id;
	
	private double lc_e = 10.0;
	private int uc_L = 100;
	private int uc_N = 100;
	private boolean lc_n = false;

	public String getValues()
	{
		String info = 	"## Output options:" +
						"##\n" +
						"# Print hits with e-value below this value\n" +
						"EVAL=" + getLc_e() + "\n" +
						"# Number of hits to show in the result list \n" +
						"NOHITS=" + getUc_L() + "\n" +
						"# Number of alignments to show in the output\n" +
						"NOALNS=" + getUc_N() + "\n";
		
		info += "# Do not how statistical parameters below alignments\n";
		if(!isLc_n())
		{
			info += "SHOW=1\n";
		}
		else
		{
			info += "SHOW=0\n";
		}
		
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLc_e() {
		return lc_e;
	}

	public void setLc_e(double lc_e) {
		this.lc_e = lc_e;
	}

	public int getUc_L() {
		return uc_L;
	}

	public void setUc_L(int uc_L) {
		this.uc_L = uc_L;
	}

	public int getUc_N() {
		return uc_N;
	}

	public void setUc_N(int uc_N) {
		this.uc_N = uc_N;
	}

	public boolean isLc_n() {
		return lc_n;
	}

	public void setLc_n(boolean lc_n) {
		this.lc_n = lc_n;
	}

}
