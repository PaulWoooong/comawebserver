package bioinfo.comaWebServer.entities;

/*Information correction options:
	-J <info>       [Real]      Upper bound of information content threshold   (0.30)
	                            used in 2nd-pass computations.
	                            0 -- disables information correction
	-j <numerator>  [Real]      Numerator of expression to compute 2nd-pass     (4.4)
	                            inf. content threshold (J+j/(log(E)-l)).
	-l <scale>      [Real]      Logarithmic scale to compute 2nd-pass           (4.0)
	                            inf. content threshold (J+j/(log(E)-l)).
	                            0 -- disables information correction
	-b <numerator>  [Real]      Numerator of alternative expression to compute  (1.0)
	                            inf. content threshold (-b/(log(E)+B)).
	-B <scale>      [Real]      Logarithmic scale to alternatively compute      (3.0)
	                            inf. content threshold (-b/(log(E)+B)).*/

public class InformationCorrection extends AbstractParameter
{
	private long id;
	
	private double uc_J = 0.3;
	private double lc_j = 4.4;
	private double lc_l = 4.0;
	private double lc_b = 1.0;
	private double uc_B = 3.0;
	
	public String getValues()
	{
		String info = "#\n# Information correction options:\n#\n" +
					  "# Upper bound (J) of information content threshold used in 2nd-pass computations\n" +
					  "INFCON2UB=" + getUc_J() + "\n" +
					  "# Numerator (j) of expression to compute 2nd-pass inf. content threshold (J+j/(log(E)-l))\n" +
					  "INFCON2NUMER=" + getLc_j() + "\n" +
					  "# Logarithmic scale (l) to compute 2nd-pass information content threshold (J+j/(log(E)-l))\n" +
					  "INFCON2LOGSCALE=" + getLc_l() + "\n" +
					  "# Numerator (b) of alternative expression to compute inf. content threshold (-b/(log(E)+B))\n" +
					  "INFCONALTNUMER=" + getLc_b() + "\n" +
					  "# Logarithmic scale (B) to alternatively compute inf. content threshold (-b/(log(E)+B))\n" +
					  "INFCONALTLOGSCALE=" + getUc_B() + "\n";
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getUc_J() {
		return uc_J;
	}

	public void setUc_J(double uc_J) {
		this.uc_J = uc_J;
	}

	public double getLc_j() {
		return lc_j;
	}

	public void setLc_j(double lc_j) {
		this.lc_j = lc_j;
	}

	public double getLc_l() {
		return lc_l;
	}

	public void setLc_l(double lc_l) {
		this.lc_l = lc_l;
	}

	public double getLc_b() {
		return lc_b;
	}

	public void setLc_b(double lc_b) {
		this.lc_b = lc_b;
	}

	public double getUc_B() {
		return uc_B;
	}

	public void setUc_B(double uc_B) {
		this.uc_B = uc_B;
	}
}
