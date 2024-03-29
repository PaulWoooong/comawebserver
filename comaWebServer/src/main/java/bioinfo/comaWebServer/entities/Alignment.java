package bioinfo.comaWebServer.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import bioinfo.comaWebServer.enums.Scheme;

/*Alignment options:
	-G <open_cost>  [Integer|   Gap opening cost. 0 -- UNGAPPED alignments.      (A4)
	                 A[1-50]]   A -- automatically computed costs. Optional
	                            number after A specifies autocorrelation
	                            window size.
	-X <extension>  [Integer]   Initial gap extension cost.                       (1)
	-g <coefficient>[0-1.0]     Deletion probability weight.                    (0.6)
	-S <scheme>     (profile|   Scoring scheme to use:                      (profile)
	                 global)     profile -- profile-specific scoring,
	                             global -- within context of database.
	-C                          Do not apply composition-based statistics.
	-c                          Do not use probabilities for gap costs.
*/
public class Alignment extends AbstractParameter
{	
	private long id;	
	private String uc_G = "A4";
	private int uc_X = 1;
	private double lc_g = 0.6;
	private Scheme scheme = Scheme.PROFILE;
	private boolean uc_C = false;
	private boolean lc_c = false;
	private DatabaseItem databaseItem;
	
	public String getValues()
	{
		String values = "#\n# Alignment options:\n#\n";
		values += "# Profile database\n";
		values += "d=" + databaseItem.getValues() + "\n";
		values += "# Gap opening cost\n";
		values += "G=" + uc_G + "\n";
		values += "# Initial gap extension cost\n";
		values += "X=" + String.valueOf(uc_X) + "\n";
		values += "# Deletion probability weight\n";
		values += "g=" + String.valueOf(lc_g) + "\n";
		values += "# Scoring scheme to use\n";
		values += "S=" + scheme.getScheme() + "\n";
		
		values += "# Do not apply composition-based statistics\n";
		if(uc_C)
		{
			values += "C=1\n";
		}
		else
		{
			values += "C=0\n";
		}
		values += "# Do not use probabilities for gap costs\n";
		if(lc_c)
		{
			values += "c=1\n";
		}
		else
		{
			values += "c=0\n";
		}
		
		return values;
	}

	public String getUc_G() {
		return uc_G;
	}

	public void setUc_G(String uc_G) {
		this.uc_G = uc_G;
	}

	public int getUc_X() {
		return uc_X;
	}

	public void setUc_X(int uc_X) {
		this.uc_X = uc_X;
	}

	public double getLc_g() {
		return lc_g;
	}

	public void setLc_g(double lc_g) {
		this.lc_g = lc_g;
	}

	public boolean isUc_C() {
		return uc_C;
	}

	public void setUc_C(boolean uc_C) {
		this.uc_C = uc_C;
	}

	public boolean isLc_c() {
		return lc_c;
	}

	public void setLc_c(boolean lc_c) {
		this.lc_c = lc_c;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public DatabaseItem getDatabaseItem() {
		return databaseItem;
	}

	public void setDatabaseItem(DatabaseItem databaseItem) {
		this.databaseItem = databaseItem;
	}
}
