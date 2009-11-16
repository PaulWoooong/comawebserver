package bioinfo.comaWebServer.entities;

import bioinfo.comaWebServer.enums.Commands;

public class Cluster 
{
	private long id;
	private String hostname;
	private int port = 22;
	private String username;
	private String privateKeyPath;
	private String passphrase;
	private String commandComa;
	private String commandModeller;
	private String commandMsa;
	private String tmpFileStoragePath;
	private String tmpLocalFileStoragePath = "webapps/comaWebServer/user_data/";
	private String tmpLocalFileStoragePathForImg = "/user_data/";
	private String urlForResults;
	
	public String getComaCommand()
	{
		String command = getCommandComa();
		if(command == null)
		{
			command = "command";
		}
		return getComaCommand(command, "<PATH>", "<ID>");
	}
	
	public String getModellerCommand()
	{
		String command = getCommandModeller();
		if(command == null)
		{
			command = "command";
		}
		return getModellerCommand(command, "<PATH>", "<ID>");
	}
	
	public String getMsaCommand()
	{
		String command = getCommandMsa();
		if(command == null)
		{
			command = "command";
		}
		return getModellerCommand(command, "<PATH>", "<ID>");
	}
	
	public static String getComaCommand(String command, String path, String id)
	{
		return 	command + " " +
					Commands.COMA_PARAM_ID.getValue() + " " +
					id + " " +
					Commands.COMA_PARAM_PATH.getValue() + " " +
					path;
	}
	
	public static String getModellerCommand(String command, String path, String id)
	{
		return 	command + " " +
					Commands.MODELLER_PARAM_ID.getValue() + " " +
					id + " " +
					Commands.MODELLER_PARAM_PATH.getValue() + " " +
					path;
	}
	
	public static String getMsaCommand(String command, String path, String id)
	{
		return 	command + " " +
					Commands.MSA_PARAM_ID.getValue() + " " +
					id + " " +
					Commands.MSA_PARAM_PATH.getValue() + " " +
					path;
	}
	
	public static String getJobStat(String pbsId)
	{
		return 	Commands.PBS_JOB_STAT.getValue() + " " + pbsId;
	}
	
	public static String testFileExists(String filePath)
	{
		return Commands.FILE_EXISTS.getValue() + " " + filePath;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPrivateKeyPath() {
		return privateKeyPath;
	}
	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getTmpFileStoragePath() 
	{
		if(tmpFileStoragePath != null && 
				tmpFileStoragePath.charAt(tmpFileStoragePath.length() - 1) != '/')
		{
			tmpFileStoragePath += "/";
		}
		return tmpFileStoragePath;
	}
	public void setTmpFileStoragePath(String tmpFileStoragePath) {
		this.tmpFileStoragePath = tmpFileStoragePath;
	}
	public String getCommandComa() 
	{
		return commandComa;
	}
	public void setCommandComa(String commandComa) {
		this.commandComa = commandComa;
	}
	public String getCommandModeller() {
		return commandModeller;
	}
	public void setCommandModeller(String commandModeller) {
		this.commandModeller = commandModeller;
	}

	public String getUrlForResults() 
	{
		if(urlForResults != null && 
				urlForResults.charAt(urlForResults.length() - 1) != '/')
		{
			urlForResults += "/";
		}
		return urlForResults;
	}
	public void setUrlForResults(String urlForResults) {
		this.urlForResults = urlForResults;
	}

	public String getTmpLocalFileStoragePath() 
	{
		if(tmpLocalFileStoragePath != null && 
				tmpLocalFileStoragePath.charAt(tmpLocalFileStoragePath.length() - 1) != '/')
		{
			tmpLocalFileStoragePath += "/";
		}
		return tmpLocalFileStoragePath;
	}

	public void setTmpLocalFileStoragePath(String tmpLocalFileStoragePath) {
		this.tmpLocalFileStoragePath = tmpLocalFileStoragePath;
	}

	public String getCommandMsa() {
		return commandMsa;
	}

	public void setCommandMsa(String commandMsa) {
		this.commandMsa = commandMsa;
	}

	public String getTmpLocalFileStoragePathForImg() 
	{
		if(tmpLocalFileStoragePathForImg != null && 
				tmpLocalFileStoragePathForImg.charAt(tmpLocalFileStoragePathForImg.length() - 1) != '/')
		{
			tmpLocalFileStoragePathForImg += "/";
		}
		return tmpLocalFileStoragePathForImg;
	}

	public void setTmpLocalFileStoragePathForImg(
			String tmpLocalFileStoragePathForImg) {
		this.tmpLocalFileStoragePathForImg = tmpLocalFileStoragePathForImg;
	}

}
