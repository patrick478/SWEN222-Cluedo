package cluedo;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GUICanvas extends Canvas{
	Image boardImage;
	
	public GUICanvas()
	{
		this.boardImage = makeImage("src/resources/board.jpg");
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(this.boardImage, 0, 0, null);
		double xOffset = 14.5;
		double yOffset = 14.5;
		double xSize = 19.5;
		double ySize = 19.75;
		for(int x = 0; x < 24; x++)
		{
			for(int y = 0; y < 29; y++)
			{
				g.setColor(Color.RED);
				g.drawRect((int)((x * xSize) + xOffset),(int)((y * ySize) + yOffset), (int)xSize, (int)ySize);
			}
		}
	}
	
	private BufferedImage makeImage(String filename)
	{		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return img;
	}	
}
