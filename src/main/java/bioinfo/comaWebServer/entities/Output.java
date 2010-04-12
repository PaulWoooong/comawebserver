package bioinfo.comaWebServer.entities;

/*Output options:
	-e <e_value>    [Real]      E-value threashold.       (10.0)
	-L <no_hits>    [Integer]   Maximal number of hits to show.      (500)
	-N <no_alns>    [Integer]   Maximal number of alignments to show.     (500)
	-n                          Do not show statistical parameters below
	                            alignments.*/
public class Output extends AbstractParameter
{
	private long id;
	
	private double lc_e = 10.0;
	private int uc_L = 500;
	private int uc_N = 500;
	private boolean lc_n = false;

	public String getValues()
	{
		String info = "#\n# Output options:\n#\n" +
					  "# E-value threashold\n" +
					  "e=" + getLc_e() + "\n" +
					  "# Maximal number of hits to show\n" +
					  "L=" + getUc_L() + "\n" +
					  "# Maximal number of alignments to show\n" +
					  "N=" + getUc_N() + "\n";
		
		info += "# Do not show statistical parameters below alignments\n";
		if(isLc_n())
		{
			info += "n=1\n";
		}
		else
		{
			info += "n=0\n";
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
