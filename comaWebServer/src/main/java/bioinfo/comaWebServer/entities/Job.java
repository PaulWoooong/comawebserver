package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import bioinfo.comaWebServer.enums.JobType;

public class Job implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	public static final String REGISTERED	= "REGISTERED";
	public static final String SUBMITTED 	= "SUBMITTED"; 
	public static final String QUEUED 		= "QUEUED";
	public static final String WAITING 		= "WAITING";
	public static final String RUNNING 		= "RUNNING";
	public static final String FINISHED 	= "FINISHED";
	public static final String ERRORS 		= "ERRORS";
	public static final String CANCELED 	= "CANCELED";
	
	private long 		id;
	private String		generatedId;
	private String		pbsId;
	
	private ComaResults comaResults;
	
	private String 	description;
	private String 	email;
	private String		status = REGISTERED;
	private String		log;
	private JobType 	type;
	private Date 		expirationDate;
	
	private String 	localPath;
	private String 	remotePath;
	
	public String getLocalFilePath(String ext)
	{
		return getLocalPath() + getGeneratedId() + ext;
	}
	
	public String getRemoteFilePath(String ext)
	{
		return getRemotePath() + getGeneratedId() + ext;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGeneratedId() {
		return generatedId;
	}
	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Enumerated(EnumType.STRING)
	public JobType getType() {
		return type;
	}
	public void setType(JobType type) {
		this.type = type;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public ComaResults getComaResults() {
		return comaResults;
	}

	public void setComaResults(ComaResults comaResults) {
		this.comaResults = comaResults;
	}

	public String getPbsId() {
		return pbsId;
	}

	public void setPbsId(String pbsId) {
		this.pbsId = pbsId;
	}
}
