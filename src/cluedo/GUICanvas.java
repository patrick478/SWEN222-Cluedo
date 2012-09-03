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
