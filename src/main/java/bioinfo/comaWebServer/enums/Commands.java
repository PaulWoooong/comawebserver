package bioinfo.comaWebServer.enums;

public enum Commands 
{
	COMA_PARAM_ID			("-i"),
	COMA_PARAM_PATH 		("-p"),
	
	MSA_PARAM_ID			("-i"),
	MSA_PARAM_PATH 			("-p"),
	
	PBS_JOB_STAT	 		("qstat"),
	FILE_EXISTS		 		("ls"),
	FILE_MODIFICATION		("stat -c %y "),
	
	MODELLER_PARAM_ID	 	("-i"),
	MODELLER_PARAM_PATH  	("-p");

	
	private final String value;
	
	Commands(String s)
	{
		this.value = s;
	}
	
	public String getValue()
	{
		return value;
	}
}
