package bioinfo.comaWebServer.util;

import java.util.ArrayList;
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
		if(lines.size() == 0)
		{
			return false;
		}
		
		String fastaHeader  = "^>\\w+.*";
		String fastaData = "[ABCDEFGHIKLMNOPQRSTUVWYZXabcdefghiklmnopqrstuvwyzx*\\-\\s]+";	
		
		List<String> fasta = new ArrayList<String>();
		for(int i = 0; i < lines.size();)
		{
			String line = lines.get(i);
			if(line.matches(fastaHeader))
			{
				fasta.add(line);
				i++;
			}
			else
			{
				StringBuffer buffer = new StringBuffer();
				for(; i < lines.size() && !lines.get(i).matches(fastaHeader); i++)
				{
					buffer.append(lines.get(i));
				}
				fasta.add(buffer.toString());
			}
		}
		
		if(fasta.size() % 2 != 0)
		{
			return false;
		}
		
		int lineLength = fasta.get(1).length();
		
		for(int i = 0; i < fasta.size();)
		{
			String header = fasta.get(i);
			if(!header.matches(fastaHeader))
			{
				return false;
			}
			
			String data = fasta.get(i + 1);
			if(data.length() != lineLength)
			{
				return false;
			}
			if(!data.matches(fastaData))
			{
				return false;	
			}
			
			i += 2;
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
