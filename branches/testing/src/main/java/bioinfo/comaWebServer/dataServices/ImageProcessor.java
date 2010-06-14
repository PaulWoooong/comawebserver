package bioinfo.comaWebServer.dataServices;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor 
{
	public static String extention = "jpg";
	private int width = 700;
	private int height = 12;
	private int length;
	
	private double onePixelWeight;
	
	public static void main(String[] args)
	{
		ImageProcessor imageProcessor = new ImageProcessor(5);
		try 
		{
			imageProcessor.draw("tmp.jpg", Color.RED, "PF01939", 2, 3);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ImageProcessor(int length)
	{
		this.length = length;
		onePixelWeight = width / (double)length;
	}
	
	public void draw(String name, Color color, String text,
						int subbegin, int subend) throws IOException
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		
		int recBegin = (int) (subbegin * onePixelWeight);
		int recWidth = (int) ((subend - subbegin + 1) * onePixelWeight);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		g2.setColor(color);
		g2.fillRect(recBegin, 0, recWidth, height);
		
		Font font = null;
		{
			try 
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File("fonts" + File.separator + "VERDANAB.TTF"));
				font = f.deriveFont(height - 1f);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				font = new Font(Font.DIALOG, Font.BOLD, height - 1);
			}

		}
		g2.setFont(font);
		FontRenderContext frc = g2.getFontRenderContext();
		
		int textWidth = 1 + (int)font.getStringBounds(text, frc).getWidth();
		
		if(textWidth < recWidth)
		{
			g2.setColor(Color.WHITE);
			g2.drawString(text, recBegin + (recWidth - textWidth) / 2, height - 2);
		}
		else
		{
			g2.setColor(Color.BLACK);
			
			int left = recBegin;
			int right = width - (recBegin + recWidth);
			
			if(textWidth + 5 < left)
			{
				g2.drawString(text, recBegin - textWidth - 5, height - 2);
			}
			else if(textWidth + 5 < right)
			{
				g2.drawString(text, recBegin + recWidth + 5, height - 2);
			}
			else
			{
				g2.drawString(text, 10, height - 2);
			}
		}
		
	    File outputfile = new File(name);
	    ImageIO.write(image, extention, outputfile);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getOnePixelWeight() {
		return onePixelWeight;
	}

	public void setOnePixelWeight(double onePixelWeight) {
		this.onePixelWeight = onePixelWeight;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
