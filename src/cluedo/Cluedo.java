package cluedo;

import java.util.*;

public class Cluedo {
	
	private final int MaxPlayers = 6;
	private int nPlayers = 0;
	private boolean running = false;
	
	private CluedoUI uiVendor;
	
	public Cluedo(int numPlayers)
	{
		this.nPlayers = numPlayers;
	}
	
	public void Start() throws CluedoException
	{
		if(this.nPlayers > this.MaxPlayers)
			throw new CluedoException(String.format("The number of players must be less than or equal to %d\n", this.MaxPlayers));
		
		this.running = true;
		
		if(this.uiVendor == null)
			this.uiVendor = new TextCluedoUI();
				
		Board b = new Board();
		b.Setup();
		
		// Debug
		//b.PrintBoard();
		
		this.uiVendor.DisplayMessage(CluedoMessage.WelcomeMessage);
		
		List<Player> players = new ArrayList<Player>();
		for(int i = 0; i < this.nPlayers; i++) 
		{			
			Player newPlayer = new Player();
			newPlayer.SetCharacter(this.uiVendor.ChooseCharacter(i+1));
			players.add(newPlayer);
		}
		
		while(this.running)
		{
			for(int i = 0; i < this.nPlayers; i++)
			{
				uiVendor.SetTurn(i+1, players.get(i));
				players.get(i).TakeTurn();
			}
		}
	}
}
