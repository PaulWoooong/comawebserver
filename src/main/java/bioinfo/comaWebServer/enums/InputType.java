package bioinfo.comaWebServer.enums;

public enum InputType 
{
	FASTA	("FASTA");

	private final String name;
	
	InputType(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
