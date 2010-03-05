package bioinfo.comaWebServer.entities;

import bioinfo.comaWebServer.enums.Commands;

public class Cluster 
{
	private long id;
	
	private String username;
	private String globalFilePath;
	private String localFilePath;
	private String commandComa;
	private String commandModeller;
	private String commandMsa;
	private String urlForResults;
	private boolean local = false;
	
	private String hostname;
	private int port = 22;
	private String privateKeyPath;
	private String passphrase;
	private String remoteFilePath;
	
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
	public String getRemoteFilePath() 
	{
		if(remoteFilePath != null && 
				remoteFilePath.charAt(remoteFilePath.length() - 1) != '/')
		{
			remoteFilePath += "/";
		}
		return remoteFilePath;
	}
	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
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

	public String getCommandMsa() {
		return commandMsa;
	}

	public void setCommandMsa(String commandMsa) {
		this.commandMsa = commandMsa;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public String getGlobalFilePath() {
		return globalFilePath;
	}

	public void setGlobalFilePath(String globalFilePath) {
		this.globalFilePath = globalFilePath;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

}
