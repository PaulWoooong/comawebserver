package bioinfo.comaWebServer.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import bioinfo.comaWebServer.enums.InputType;
import bioinfo.comaWebServer.exceptions.FormatException;

public class Validator 
{
	
	public static void check(File file, InputType format) throws Exception
	{
		if(format == InputType.FASTA)
		{
			checkFasta(file);
		}
		checkFasta(file);
	}
	
	private static void checkFasta(File file) throws Exception
	{
	    FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedReader br = null;
		
		try 
		{
			fis = new FileInputStream(file);

			bis = new BufferedInputStream(fis);
			br = new BufferedReader(new InputStreamReader(bis));

			String fastaHeader  = "^>\\w+.*";
			String fastaData = "[ABCDEFGHIKLMNOPQRSTUVWYZXabcdefghiklmnopqrstuvwyzx*-]+";
			
			String line = br.readLine();
			if(line == null)
			{
				throw new FormatException("Incorrect input format: FASTA!");
			}
			
			while (line != null) 
			{
				if(!line.matches(fastaHeader))
				{
					throw new FormatException("Incorrect input format: FASTA!");
				}
				
				line = br.readLine();
				if(line == null || !line.matches(fastaData))
				{
					throw new FormatException("Incorrect input format: FASTA!");
				}
				
				boolean data = true;
				while(line != null && !line.equals("") && data && (line = br.readLine()) != null)
				{
					if(!line.matches(fastaData))
					{
						data = false;
					}
				}
				
				while(line != null && line.equals("") && (line = br.readLine()) != null){}
			}
		} 
		catch (IOException e) 
		{
			e = new IOException("Validator: failed to process an input sequence!");
			throw e;
		}
		
		finally
		{
			if(fis != null) fis.close();
			if(bis != null) bis.close();
			if(br  != null) br.close();	
		}
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
