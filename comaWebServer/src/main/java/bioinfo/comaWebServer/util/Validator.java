package bioinfo.comaWebServer.util;

import java.util.List;

import bioinfo.comaWebServer.enums.InputType;

public class Validator 
{
	
	public static boolean check(List<String> lines, InputType format) throws Exception
	{
		if(format == InputType.FASTA)
		{
			return checkFasta(lines);
		}
		else
		{
			throw new Exception("Unknown input format: " + format);
		}
	}
	
	private static boolean checkFasta(List<String> lines)
	{
		String fastaHeader  = "^>\\w+.*";
		String fastaData = "[ABCDEFGHIKLMNOPQRSTUVWYZXabcdefghiklmnopqrstuvwyzx*-]+";
		
		if(lines.size() == 0)
		{
			return false;
		}
		
		for(int i = 0; i < lines.size();)
		{
			String currLine = lines.get(i);
			
			if(!currLine.matches(fastaHeader))
			{
				return false;
			}
			
			i++;
			
			if(i < lines.size())
			{
				currLine = lines.get(i);
				
				if(!currLine.matches(fastaData))
				{
					return false;	
				}
			}
			else
			{
				return false;
			}
			
			boolean data = true;
			
			while(i < lines.size() && data)
			{
				currLine = lines.get(i);
				
				if(!currLine.matches(fastaData))
				{
					data = false;
				}
				else
				{
					i++;	
				}
			}
			
			boolean emptyLines = true;
			
			while(i < lines.size() && emptyLines)
			{
				currLine = lines.get(i);
				
				if(!currLine.equals(""))
				{
					emptyLines = false;
				}
				else
				{
					i++;	
				}
			}
		}
		
		return true;
	}
	
	public static boolean checkAlignment_G(String value)
	{
		if(value.length() > 3) return false;
		
		if(value.length() == 1 && !value.equals("0")) return false;
		
		if(value.length() > 1 && !value.substring(0, 1).equals("A")) return false;
		
		int number = 0;
		try 
		{
			number = Integer.parseInt(value.substring(1));
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
		
		if(number < 1 || number > 50) return false;
		
		return true;
	}
}
