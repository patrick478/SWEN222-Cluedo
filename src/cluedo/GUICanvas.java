package cluedo;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GUICanvas extends Canvas{
	Image boardImage;
	
	Color scarletColor;
	Color mustardColor;
	Color whiteColor;
	Color greenColor;
	Color peacockColor;
	Color plumColor;
	
	private boolean drawGrid = false;
	
	public GUICanvas()
	{
		this.boardImage = makeImage("src/resources/board.jpg");
		this.scarletColor = Color.RED;
		this.mustardColor = Color.YELLOW;
		this.whiteColor = Color.WHITE;
		this.greenColor = Color.GREEN;
		this.peacockColor = new Color(128, 0, 128);
		this.plumColor = new Color(142, 69, 133);
		
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(this.boardImage, 0, 0, null);
		
		if(this.drawGrid)
		{
			for(int x = 0; x < 24; x++)
			{
				for(int y = 0; y < 29; y++)
				{
					g.setColor(Color.RED);
					g.drawRect((int)((x * GUI.xSize) + GUI.xOffset),(int)((y * GUI.ySize) + GUI.yOffset), (int)GUI.xSize, (int)GUI.ySize);
				}
			}
		}
		
		for(Character c : Character.characters)
		{
			System.out.printf("%d %d\n",  c.X, c.Y);
			Color targetColour = Color.BLACK;
			
			/*
			 * new Character("Kasandra Scarlet", 18, 28), 
			new Character("Jack Mustard", 7, 28),
			new Character("Diane White", 0, 19), 
			new Character("Jacob Green", 0, 9), 
			new Character("Eleanor Peacock", 20, 0), 
			new Character("Victor Plum", 6, 0)
			*/
			
			if(c == Character.characters[0])
				targetColour = scarletColor;
			else if(c == Character.characters[1])
				targetColour = mustardColor;
			
			g.setColor(targetColour);
			g.fillOval(GUI.getCoordFromBoardX(c.X) + 2, GUI.getCoordFromBoardY(c.Y) + 2, (int)(GUI.xSize - 3), (int)(GUI.ySize - 3));
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
