package bioinfo.comaWebServer.entities;

/*SEG options:
	-U                          Invoke low-complexity filtering of query.
	-V                          Invoke LC filtering for each sequence in
	                            alignment using same parameters below.
	-y <window>     [Integer]   Window length.                                   (12)
	-z <low>        [Real]      Low entropy threshold.                          (2.2)
	-Z <high>       [Real]      High entropy threshold.                         (2.5)
	-D <distance>   [Real]      Distance of equivalence between profile       (12.96)
	                            vectors.*/

public class SEG extends AbstractParameter
{
	private long id;
	
	private boolean uc_U;
	private boolean uc_V;
	
	private int lc_y = 12;
	
	private double lc_z = 2.2;
	private double uc_Z = 2.5;
	private double uc_D = 12.96;
	
	public String getValues()
	{
		String info = "#\n# SEG options:\n#\n";
		
		info += "# Invoke low-complexity filtering of query\n";
		if(isUc_U())
		{
			info += "INVLCFILTER=1" + "\n";
		}
		else
		{
			info += "INVLCFILTER=0" + "\n";
		}
		
		info += "# Invoke LC filtering for each sequence in alignment using same parameters below\n";
		if(isUc_V())
		{
			info += "LCFILTEREACH=1" + "\n";
		}
		else
		{
			info += "LCFILTEREACH=0" + "\n";
		}
		
	   info += "# Window length\n" +
			  "LCWINDOW=" + getLc_y()  + "\n" +
			  "# Low entropy threshold\n" +
			  "LCLOWENT=" + getLc_z()  + "\n" +
			  "# High entropy threshold\n" +
			  "LCHIGHENT=" + getUc_Z()  + "\n" +
			  "# Distance of equivalence between profile vectors\n" +
			  "DISTANCE=" + getUc_D()  + "\n";
			   
		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isUc_U() {
		return uc_U;
	}

	public void setUc_U(boolean uc_U) {
		this.uc_U = uc_U;
	}

	public boolean isUc_V() {
		return uc_V;
	}

	public void setUc_V(boolean uc_V) {
		this.uc_V = uc_V;
	}

	public int getLc_y() {
		return lc_y;
	}

	public void setLc_y(int lc_y) {
		this.lc_y = lc_y;
	}

	public double getLc_z() {
		return lc_z;
	}

	public void setLc_z(double lc_z) {
		this.lc_z = lc_z;
	}

	public double getUc_Z() {
		return uc_Z;
	}

	public void setUc_Z(double uc_Z) {
		this.uc_Z = uc_Z;
	}

	public double getUc_D() {
		return uc_D;
	}

	public void setUc_D(double uc_D) {
		this.uc_D = uc_D;
	}

}
