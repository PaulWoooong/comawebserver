package bioinfo.comaWebServer.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.tapestry5.beaneditor.NonVisual;

import bioinfo.comaWebServer.dataManagement.JobStatus;
import bioinfo.comaWebServer.enums.JobType;

public class Job implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private long 		id;
	private String		generatedId;
	@NonVisual
	private String		pbsId;
	@NonVisual
	private ComaResults comaResults;
	
	private String 		description;
	private String 		email;
	private String		status = JobStatus.REGISTERED.getStatus();
	@NonVisual
	private String		log;
	private JobType 	type;
	private Date 		expirationDate;
	@NonVisual
	private Set<DataFile> dataFiles;
	@NonVisual
	private String 	localPath;
	@NonVisual
	private String 	remotePath;
	
	public String getLocalFilePath(String ext)
	{
		return getLocalPath() + getGeneratedId() + ext;
	}
	
	public String getRemoteFilePath(String ext)
	{
		return getRemotePath() + getGeneratedId() + ext;
	}
	
	public String showName()
	{
		if(description != null)
		{
			if(description.length() > 20)
			{
				return description.substring(0, 17) + "..."; 
			}
			return description;
		}
		
		if(generatedId.length() > 20)
		{
			return generatedId.substring(0, 17) + "..."; 
		}
		return generatedId;
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

	public Set<DataFile> getDataFiles() 
	{
		if(dataFiles == null)
		{
			dataFiles = new HashSet<DataFile>();
		}
		return dataFiles;
	}

	public void setDataFiles(Set<DataFile> dataFiles) {
		this.dataFiles = dataFiles;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
