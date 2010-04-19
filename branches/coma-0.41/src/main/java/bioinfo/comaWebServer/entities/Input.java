package bioinfo.comaWebServer.entities;

import org.apache.tapestry5.upload.services.UploadedFile;

import bioinfo.comaWebServer.enums.InputType;

public class Input 
{
	private InputType type 			= InputType.FASTA;
	private String sequence 		= null;
	private String description 		= null;
	private String email 			= null;
	private UploadedFile file 		= null;
	
	public InputType getType() {
		return type;
	}
	public void setType(InputType type) {
		this.type = type;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
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
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
