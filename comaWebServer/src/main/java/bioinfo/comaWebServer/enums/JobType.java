package bioinfo.comaWebServer.enums;

public enum JobType 
{
	COMA_JOB		("COMA"),
	MODELLER_JOB	("MODELLER"),
	MSA_JOB			("MSA");

	private final String type;
	
	JobType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}
