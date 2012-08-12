package cluedo;

import java.util.*;

public class Game {
	
	private final int MaxPlayers = 6;
	private int nPlayers = 0;
	private boolean running = false;
	
	private CluedoUI uiVendor;
	
	public Game(int numPlayers)
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
		
		Character murderCharacter = Character.getRandom();
		Weapon murderWeapon = Weapon.getRandom();
		Room murderRoom = Room.getRandom();
		
		// Debug
		//b.PrintBoard();
		
		this.uiVendor.DisplayMessage(CluedoMessage.WelcomeMessage);
		
		List<Player> players = new ArrayList<Player>();
		for(int i = 0; i < this.nPlayers; i++) 
		{			
			Player newPlayer = new Player();
			newPlayer.SetCharacter(this.uiVendor.ChooseCharacter(i+1));
			newPlayer.SetPosition(newPlayer.GetCharacter().startPosX, newPlayer.GetCharacter().startPosY);
			players.add(newPlayer);
		}
		
		List<GameObject> available = new ArrayList<GameObject>();
		available.addAll(Arrays.asList(Character.characters));
		available.addAll(Arrays.asList(Weapon.weapons));
		available.addAll(Room.rooms.values());
		
		// TODO those lists need to be shuffled. before they're added, too.
		available.remove(murderWeapon);
		available.remove(murderRoom);
		available.remove(murderCharacter);
		
		int cPlayer = 0;
		for(int i = 0; i < available.size(); i++)
		{
			players.get(cPlayer++).GiveCard(available.get(i));
			cPlayer %= this.nPlayers;
		}
		
		while(this.running)
		{
			for(int i = 0; i < this.nPlayers; i++)
			{
				uiVendor.SetTurn(i+1, players.get(i), b);
				Guess g = players.get(i).TakeTurn(uiVendor, b);
				if(g.isAccusation)
				{
					if(g.wep == murderWeapon && g.room == murderRoom && g.murderer == murderCharacter)
					{
						// WINNER
						uiVendor.DisplayMessage(CluedoMessage.Winner, i+1, players.get(i));
						this.running = false;
						break;
					}
					else
					{
						//LOSER
						uiVendor.DisplayMessage(CluedoMessage.Loser, i+1, players.get(i));
						this.running = false;
						break;
					}
				}
				else
				{
					GameObject givenCard = null;
					for(int j = 0; j < this.nPlayers; j++)
					{
						
						if(j == i) continue;
						if(players.get(j).cards.contains(g.murderer) && !players.get(i).cards.contains(g.murderer))
						{
							givenCard = g.murderer;
							break;
						}
						else if(players.get(j).cards.contains(g.wep) && !players.get(i).cards.contains(g.wep))
						{
							givenCard = g.wep;
							break;
						}
						else if(players.get(j).cards.contains(g.room) && !players.get(i).cards.contains(g.room))
						{
							givenCard = g.room;
							break;
						}
					}
					if(givenCard != null)
					{
						uiVendor.DisplayMessage(CluedoMessage.FoundCard, givenCard);
						players.get(i).GiveCard(givenCard);
					}
					else
						uiVendor.DisplayMessage(CluedoMessage.NoCards);
				}
			}
		}
	}
}
