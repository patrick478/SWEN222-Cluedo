package cluedo;

import java.util.*;

public class Cluedo {
	
	private final int MaxPlayers = 6;
	private int nPlayers = 0;
	
	private CluedoUI uiVendor;
	
	public Cluedo(int numPlayers)
	{
		this.nPlayers = numPlayers;
	}
	
	public void Start() throws CluedoException
	{
		if(this.nPlayers > this.MaxPlayers)
			throw new CluedoException(String.format("The number of players must be less than or equal to %d\n", this.MaxPlayers));
		
		if(this.uiVendor == null)
			this.uiVendor = new TextCluedoUI();
				
		Board b = new Board();
		b.Setup();
		b.PrintBoard();
		
		this.uiVendor.DisplayMessage(CluedoMessage.WelcomeMessage);
		
		List<Player> players = new ArrayList<Player>();
		for(int i = 0; i < this.nPlayers; i++) 
		{			
			Player newPlayer = new Player();
			newPlayer.SetCharacter(Character.SelectCharacter());
			players.add(newPlayer);
		}
	}
}
