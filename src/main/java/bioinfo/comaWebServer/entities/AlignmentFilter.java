package bioinfo.comaWebServer.entities;

/*From makepro
Alignment filter:
	-u                          Disallow HC filter within alignment columns.
	-w <window>     [Integer]   Window length.                                  ( 30)
	-f <low>        [Real]      Low entropy threshold.                          (3.3)
	-F <high>       [Real]      High entropy threshold.                         (3.5)
*/
public class AlignmentFilter extends AbstractParameter
{
	private long id;
	private boolean lc_u = true;
	private int lc_w = 30;
	private double lc_f = 3.3;
	private double uc_F = 3.5;
	
	public String getValues()
	{
		String values = "#\n# Alignment filter:\n#\n";
		values += "# Window length\n";
		values += "HCWINDOW=" + String.valueOf(lc_w) + "\n";
		values += "# Low entropy threshold\n";
		values += "HCLOWENT=" + String.valueOf(lc_f) + "\n";
		values += "# High entropy threshold\n";
		values += "HCHIGHENT=" + String.valueOf(uc_F) + "\n";
		
		values += "# Invoke high-complexity filter in alignment columns\n";
		if(lc_u)
		{
			values += "HCFILTER=1\n";
		}
		else
		{
			values += "HCFILTER=0\n";
		}
		
		return values;
	}
	
	public boolean isLc_u() {
		return lc_u;
	}
	public void setLc_u(boolean lc_u) {
		this.lc_u = lc_u;
	}
	public int getLc_w() {
		return lc_w;
	}
	public void setLc_w(int lc_w) {
		this.lc_w = lc_w;
	}
	public double getLc_f() {
		return lc_f;
	}
	public void setLc_f(double lc_f) {
		this.lc_f = lc_f;
	}
	public double getUc_F() {
		return uc_F;
	}
	public void setUc_F(double uc_F) {
		this.uc_F = uc_F;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
