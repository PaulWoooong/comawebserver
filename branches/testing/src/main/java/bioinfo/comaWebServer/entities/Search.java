package bioinfo.comaWebServer.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import bioinfo.comaWebServer.enums.CompositionScore;
import bioinfo.comaWebServer.enums.ParamType;
import bioinfo.comaWebServer.enums.PsiBlastFilters;
import bioinfo.comaWebServer.enums.PsiBlastRadioParams;

/*RR = 1 ## restart with psi-blast anyway even if MSA is given as input
AA = 1 ## include psi-blast alternative alignments of subjects
IN (inn) = 1 ## system construcrs MSA with automatic inspection*/


public class Search extends AbstractParameter
{	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private int lc_j = 8;
	private double lc_h = 0.002;
	private CompositionScore lc_t = CompositionScore.TWO;
	private PsiBlastFilters filters = PsiBlastFilters.NO_FILTERING;
	private DatabaseItem databaseItem;
	private String type = ParamType.USER.getType();
	
	private boolean aa = false;
	
	private PsiBlastRadioParams msaStrategy = PsiBlastRadioParams.RR;
	
	public Search(){}
	
	public Search(Search search)
	{
		lc_j = search.lc_j;
		lc_h = search.lc_h;
		aa	 = search.aa;
		lc_t = search.lc_t;
		databaseItem = search.databaseItem;
		type = ParamType.USER.getType();
		msaStrategy = search.msaStrategy;
		filters = search.filters;
	}
	
	public Search(String type)
	{
		this.type = type;
	}
	
	public String getValues()
	{
		String values = "#\n# Construction of query-based multiple alignment using PSI-BLAST:\n#\n";
		
		if(msaStrategy == PsiBlastRadioParams.RR)
		{
			values += "# Configure PSI-BLAST run\n";
			values += "RR=1\n";
			
			values += "# Sequence database\n";
			values += "pd=" + databaseItem.getValues() + "\n";
			values += "# E-value threashold for inclusion\n";
			values += "ph=" + String.valueOf(lc_h) + "\n";
			values += "# Maximal number of iterations\n";
			values += "pj=" + String.valueOf(lc_j) + "\n";
			values += "# Compositional adjustments (4 choices)\n";
			values += "pt=" + String.valueOf(lc_t.getScore()) + "\n";
			
			values += "# PSI-BLAST filters\n";
			if(filters == PsiBlastFilters.NO_FILTERING)
			{
				values += "# No filtering\n";
				values += "pF=0\n";
			}
			else if(filters == PsiBlastFilters.LOW_COMPLEXITY)
			{
				values += "# Low complexity\n";
				values += "pF=1\n";
			}
			else
			{
				values += "# Mask for lookup table only\n";
				values += "pF=2\n";
			}
		}
		else if(msaStrategy == PsiBlastRadioParams.NR)
		{
			values += "# Do not run PSI-BLAST (use my input multiple alignment)\n";
			values += "RR=0\n";
		}
		else if(msaStrategy == PsiBlastRadioParams.IN)
		{
			values += "# Let the server handle PSI-BLAST run\n";
			values += "IN=1\n";

		}
		else
		{
			throw new RuntimeException("Don't know the option: " + msaStrategy.getValue());
		}
		
		values += "# Include psi-blast alternative alignments of subjects\n";
		if(aa)
		{
			values += "AA=1\n";
		}
		else
		{
			values += "AA=0\n";
		}
		
		return values;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getLc_j() {
		return lc_j;
	}
	public void setLc_j(int lc_j) {
		this.lc_j = lc_j;
	}
	public double getLc_h() {
		return lc_h;
	}
	public void setLc_h(double lc_h) {
		this.lc_h = lc_h;
	}
	@Enumerated(EnumType.STRING)
	public CompositionScore getLc_t() {
		return lc_t;
	}
	public void setLc_t(CompositionScore lc_t) {
		this.lc_t = lc_t;
	}
	public boolean isAa() {
		return aa;
	}
	public void setAa(boolean aa) {
		this.aa = aa;
	}

	public DatabaseItem getDatabaseItem() {
		return databaseItem;
	}

	public void setDatabaseItem(DatabaseItem databaseItem) {
		this.databaseItem = databaseItem;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMsaStrategy(PsiBlastRadioParams msaStrategy) {
		this.msaStrategy = msaStrategy;
	}
	@Enumerated(EnumType.STRING)
	public PsiBlastRadioParams getMsaStrategy() {
		return msaStrategy;
	}
	@Enumerated(EnumType.STRING)
	public PsiBlastFilters getFilters() {
		return filters;
	}

	public void setFilters(PsiBlastFilters filters) {
		this.filters = filters;
	}


}
