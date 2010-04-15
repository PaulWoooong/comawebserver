package bioinfo.comaWebServer.entities;

/*Autocorrection options:
	-k <numerator>  [Real]      Numerator of expression to compute 1st-pass     (5.0)
	                            autocorrection (k/sqrt(H)).
	-K <numerator>  [Real]      Numerator of expression to compute 2nd-pass     (4.0)
	                            upper bound for autocorrection (K/sqrt(H)).
	-m <scale>      [Real]      Logarithmic scale to compute 2nd-pass          (14.0)
	                            autocorrection (-1/((log(E)+m)M)).
	-M <scale>      [Real]      Denominator scale to compute 2nd-pass          (0.12)
	                            autocorrection (-1/((log(E)+m)M)).
	-p                          Analitically computed positional corrections.
	-a                          Do not compute any corrections.*/

public class Autocorrection extends AbstractParameter
{
	private long id;
	
	private double lc_k = 5.0;
	private double uc_K = 4.0;
	private double lc_m = 14.0;
	private double uc_M = 0.12;
	
	private boolean lc_p;
	private boolean lc_a;
	
	public String getValues()
	{
		String info = "#\n# Autocorrection options:\n#\n" +
					  "# Numerator (k) of expression to compute 1st-pass autocorrection (k/sqrt(H))\n" +
					  "AC1NUMER=" + getLc_k() + "\n" +
					  "# Numerator (K) of expression to compute 2nd-pass upper bound for autocorrection (K/sqrt(H))\n" +
					  "AC2UBNUMER=" + getUc_K() + "\n" + 
					  "# Logarithmic scale (m) to compute 2nd-pass autocorrection (-1/((log(E)+m)M))\n" +
					  "AC2LOGSCALE=" + getLc_m() + "\n" +
					  "# Denominator scale (M) to compute 2nd-pass autocorrection (-1/((log(E)+m)M))\n" +
					  "AC2DENOMSCALE=" + getUc_M() + "\n";
		
		info += "# Do not compute any corrections\n";
		if(isLc_a())
		{
			info += "PROHIBITCOR=1\n";
		}
		else
		{
			info += "PROHIBITCOR=0\n";
		}
		
		info += "# Compute analitically positional corrections\n";
		if(isLc_p())
		{
			info += "ANPOSCOR=1\n";
		}
		else
		{
			info += "ANPOSCOR=0\n";
		}
		
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLc_k() {
		return lc_k;
	}

	public void setLc_k(double lc_k) {
		this.lc_k = lc_k;
	}

	public double getUc_K() {
		return uc_K;
	}

	public void setUc_K(double uc_K) {
		this.uc_K = uc_K;
	}

	public double getLc_m() {
		return lc_m;
	}

	public void setLc_m(double lc_m) {
		this.lc_m = lc_m;
	}

	public double getUc_M() {
		return uc_M;
	}

	public void setUc_M(double uc_M) {
		this.uc_M = uc_M;
	}

	public boolean isLc_p() {
		return lc_p;
	}

	public void setLc_p(boolean lc_p) {
		this.lc_p = lc_p;
	}

	public boolean isLc_a() {
		return lc_a;
	}

	public void setLc_a(boolean lc_a) {
		this.lc_a = lc_a;
	}
}
