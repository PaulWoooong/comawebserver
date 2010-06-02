package bioinfo.comaWebServer.entities;

/*
## Profile construction options:
##
#   Ignore sequences in alignment file with this or     [1-100]
#   higher level of sequence identity
IDENTITY = 94
#   Perform delete state (gaps in first sequence)       (0|1)
#   computations
DELSTATE = 1
*/

public class ProfileConstruction extends AbstractParameter
{
	private long id;
	
	private int lc_t = 94;
	private boolean lc_s = true;
	
	private int lc_b = 7;
	private int uc_Y = 5;
	private int lc_x = 7;
	
	public String getValues()
	{
		String info = "## Profile construction options:\n";
		info += "##\n";
		info += "# Ignore sequences in alignment file with this or higher level of sequence identity\n";
		info += "IDENTITY=" + getLc_t() + "\n";
		
		info += "# Perform delete state (gaps in the first sequence) computations\n";
		if(isLc_s())
		{
			info += "DELSTATE=1\n";
		}
		else
		{
			info += "DELSTATE=0\n";
		}
		
		info += "# Weight for residue pseudocount frequencies\n";
		info += "PCFWEIGHT=" + lc_b  + "\n";
		info += "# Minimum fraction of alignment an extent must cover\n";
		info += "MINALNFRN=" + uc_Y  + "\n";
		info += "# Minimum number of alignment positions an extent must consist of (safeguard to -Y)\n";
		info += "MINALNPOS=" + lc_x  + "\n";
		
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLc_t() {
		return lc_t;
	}

	public void setLc_t(int lc_t) {
		this.lc_t = lc_t;
	}

	public boolean isLc_s() {
		return lc_s;
	}

	public void setLc_s(boolean lc_s) {
		this.lc_s = lc_s;
	}

	public int getLc_b() {
		return lc_b;
	}

	public void setLc_b(int lc_b) {
		this.lc_b = lc_b;
	}

	public int getUc_Y() {
		return uc_Y;
	}

	public void setUc_Y(int uc_Y) {
		this.uc_Y = uc_Y;
	}

	public int getLc_x() {
		return lc_x;
	}

	public void setLc_x(int lc_x) {
		this.lc_x = lc_x;
	}
	
}
