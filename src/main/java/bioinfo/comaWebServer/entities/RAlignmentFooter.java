package bioinfo.comaWebServer.entities;

public class RAlignmentFooter 
{
	private long id;
	private String ungappedK;
	private String ungappedLambda;
	private String gappedK;
	private String gappedLambda;
	private String entropy;
	private String expected;
	private String minMax;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUngappedK() {
		return ungappedK;
	}
	public void setUngappedK(String ungappedK) {
		this.ungappedK = ungappedK;
	}
	public String getUngappedLambda() {
		return ungappedLambda;
	}
	public void setUngappedLambda(String ungappedLambda) {
		this.ungappedLambda = ungappedLambda;
	}
	public String getGappedK() {
		return gappedK;
	}
	public void setGappedK(String gappedK) {
		this.gappedK = gappedK;
	}
	public String getGappedLambda() {
		return gappedLambda;
	}
	public void setGappedLambda(String gappedLambda) {
		this.gappedLambda = gappedLambda;
	}
	public String getEntropy() {
		return entropy;
	}
	public void setEntropy(String entropy) {
		this.entropy = entropy;
	}
	public String getExpected() {
		return expected;
	}
	public void setExpected(String expected) {
		this.expected = expected;
	}
	public String getMinMax() {
		return minMax;
	}
	public void setMinMax(String minMax) {
		this.minMax = minMax;
	}
}
