package bioinfo.comaWebServer.enums;

public enum CompositionScore 
{
	ZERO  (0),
	ONE   (1),
	TWO   (2),
	THREE (3);
	
	private final int score;
	
	CompositionScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return score;
	}
}
