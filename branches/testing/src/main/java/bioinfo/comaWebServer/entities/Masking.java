package bioinfo.comaWebServer.entities;

/*Masking options:
	-I <info>       [Real]      Mask positions of profiles with information    (0.17)
	                            content less than specified value.
	-A                          Perform any masking of profile positions
	                            after statistical parameters are computed.
	                            Implies longer alignments.
	-r <scaling>    [0-100]     Scale down masked positions by percentage        (50)
	                            specified.*/

public class Masking extends AbstractParameter
{
	private long id;
	private double uc_I = 0.17;
	private boolean uc_A;
	private int lc_r = 50;
	
	public String getValues()
	{
		String info = "#\n# Masking options:\n#\n" +
					  "# Mask positions of profiles with information content less than specified value\n" +
					  "INFCON=" + getUc_I() + "\n" +
					  "# Scale down masked positions by percentage specified\n" +
					  "SCALEDOWN=" + getLc_r() + "\n";
		
		info += "# Perform masking of profile positions after statistical parameters are computed\n";
		if(isUc_A())
		{
			info += "MASKAFTER=1\n";
		}
		else
		{
			info += "MASKAFTER=0\n";
		}
		
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getUc_I() {
		return uc_I;
	}

	public void setUc_I(double uc_I) {
		this.uc_I = uc_I;
	}

	public boolean isUc_A() {
		return uc_A;
	}

	public void setUc_A(boolean uc_A) {
		this.uc_A = uc_A;
	}

	public int getLc_r() {
		return lc_r;
	}

	public void setLc_r(int lc_r) {
		this.lc_r = lc_r;
	}
	
	
}
