package bioinfo.comaWebServer.dataManagement;



public enum JobStatus 
{
	REGISTERED			("REGISTERED"),
	SUBMITTED			("SUBMITTED"),
	QUEUED				("QUEUED"),
	RUNNING				("RUNNING"),
	FINISHED_RUNNING	("FINISHED_RUNNING"),
	FINISHED			("FINISHED"),
	ERRORS				("ERRORS"),
	CANCELED			("CANCELED");

	private final String status;
	
	JobStatus(String st)
	{
		this.status = st;
	}

	public String getStatus() {
		return status;
	}
}
