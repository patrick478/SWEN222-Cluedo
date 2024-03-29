package cluedo;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class GUICanvas extends Canvas{
	Image boardImage;
	
	Color scarletColor;
	Color mustardColor;
	Color whiteColor;
	Color greenColor;
	Color peacockColor;
	Color plumColor;
	
	private boolean drawGrid = false;
	
	private GUI ui;
	
	public GUICanvas(GUI ui)
	{
		this.boardImage = makeImage("src/resources/board.jpg");
		this.scarletColor = new Color(255, 36, 0);
		this.mustardColor = new Color(255, 128, 0);
		this.whiteColor = Color.WHITE;
		this.greenColor = new Color(0, 128, 0);
		this.peacockColor = new Color(0, 0, 255);
		this.plumColor = new Color(142, 69, 133);
		this.ui = ui;
		
	}
	
	public void paint(Graphics g)
	{
      Graphics2D g2d = (Graphics2D)g;
      // for antialising geometric shapes
      g2d.addRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING,
                                                 RenderingHints.VALUE_ANTIALIAS_ON ));
      // for antialiasing text
      g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		   
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
		
		if(ui.chooseMovementsMode)
		{			
			for(int x = 0; x < 24; x++)
			{
				for(int y = 0; y < 29; y++)
				{
					if(ui.moveables[x][y] == 1)
						g.setColor(new Color(0, 255, 0, 60));
					else if(ui.moveables[x][y] == 2)
						g.setColor(new Color(0, 0, 255, 80));
					else continue;
					
					g.fillRect((int)Math.ceil((x * GUI.xSize) + GUI.xOffset),(int)Math.ceil((y * GUI.ySize) + GUI.yOffset), (int)Math.ceil(GUI.xSize), (int)Math.ceil(GUI.ySize));
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
			else if(c == Character.characters[2])
				targetColour = whiteColor;
			else if(c == Character.characters[3])
				targetColour = greenColor;
			else if(c == Character.characters[4])
				targetColour = peacockColor;
			else if(c == Character.characters[5])
				targetColour = plumColor;
			
			g.setColor(targetColour);

			g.fillOval(GUI.getCoordFromBoardX(c.X) + 2, GUI.getCoordFromBoardY(c.Y) + 2, (int)(GUI.xSize - 4), (int)(GUI.ySize - 4));
			g.setColor(Color.BLACK);
			g.drawOval(GUI.getCoordFromBoardX(c.X) + 2, GUI.getCoordFromBoardY(c.Y) + 2, (int)(GUI.xSize - 4), (int)(GUI.ySize - 4));
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
