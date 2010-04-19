package bioinfo.comaWebServer.entities;

public class PeriodicalWorkerParams 
{
	private long id;
	private long jobExpiration = 30;
	private long jobWithErrorsExpiration = 7;
	
	private double evalueForPictures = 0.001;
	private int 	  numberForPictures = 6;
	
	private double redThreshold = 0.00001;
	private double greenThreshold = 0.0001;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getJobExpiration() {
		return jobExpiration;
	}
	public void setJobExpiration(long jobExpiration) {
		this.jobExpiration = jobExpiration;
	}
	public long getJobWithErrorsExpiration() {
		return jobWithErrorsExpiration;
	}
	public void setJobWithErrorsExpiration(long jobWithErrorsExpiration) {
		this.jobWithErrorsExpiration = jobWithErrorsExpiration;
	}
	public int getNumberForPictures() {
		return numberForPictures;
	}
	public void setNumberForPictures(int numberForPictures) {
		this.numberForPictures = numberForPictures;
	}
	public double getEvalueForPictures() {
		return evalueForPictures;
	}
	public void setEvalueForPictures(double evalueForPictures) {
		this.evalueForPictures = evalueForPictures;
	}
	public double getRedThreshold() {
		return redThreshold;
	}
	public void setRedThreshold(double redThreshold) {
		this.redThreshold = redThreshold;
	}
	public double getGreenThreshold() {
		return greenThreshold;
	}
	public void setGreenThreshold(double greenThreshold) {
		this.greenThreshold = greenThreshold;
	}
}
