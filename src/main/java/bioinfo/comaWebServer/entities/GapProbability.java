package bioinfo.comaWebServer.entities;

/*Gap probability options:
	-E <e_value>    [Real]      Evalue threshold for pair of profiles above    (1e-5)
	                            which gap prob. factor takes effect.
	-P <weight>     [Real]      Argument weight in expression of computing      (0.4)
	                            gap prob. factor: 1/(1+exp(-P*thickness+R)).
	-R <shift>      [Real]      Argument shift in expression of computing       (0.0)
	                            gap prob. factor: 1/(1+exp(-P*thickness+R)).*/

public class GapProbability extends AbstractParameter
{
	private long id;
	
	private double uc_E = 1e-5;
	private double uc_P = 0.4;
	private double uc_R = 0.0;
	
	public String getValues()
	{
		String info = "#\n# Gap probability options:\n#\n" +
					  "# Evalue threshold for pair of profiles above which gap prob. factor takes effect\n" +
					  "GPROBEVAL=" + getUc_E() + "\n" +
					  "# Argument weight (P) in expression of computing gap prob. factor: 1/(1+exp(-P*thickness+R))\n" +
					  "GPFARGWEIGHT=" + getUc_P() + "\n" +
					  "# Argument shift (R) in expression of computing gap prob. factor: 1/(1+exp(-P*thickness+R))\n" +
					  "GPFARGSHIFT=" + getUc_R() + "\n";
		
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getUc_E() {
		return uc_E;
	}

	public void setUc_E(double uc_E) {
		this.uc_E = uc_E;
	}

	public double getUc_P() {
		return uc_P;
	}

	public void setUc_P(double uc_P) {
		this.uc_P = uc_P;
	}

	public double getUc_R() {
		return uc_R;
	}

	public void setUc_R(double uc_R) {
		this.uc_R = uc_R;
	}
}
