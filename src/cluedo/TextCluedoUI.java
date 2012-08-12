package cluedo;

import java.io.IOException;
import java.util.Scanner;

public class TextCluedoUI implements CluedoUI {

	Scanner input = new Scanner(System.in);
	
	public int GetNumPlayers()
	{
		int n = -1;
		while(n < 2 || n > 6)
		{
			System.out.printf("How many players? Enter a number between 2 - 6\n\t");
		
			n = input.nextInt();
		}	
		return n;
	}
	
	
	@Override
	public void DisplayMessage(CluedoMessage msg, Object... args) {
		switch(msg)
		{
			case WelcomeMessage:
				System.out.println("================================================");
				System.out.println("================================================");

				System.out.println("             Welcome to CLUEDO");
				System.out.println("                Version 1.0");
				System.out.println("  Written by Ben Anderson and Patrick Barnes");
				System.out.println("================================================");
				System.out.println("================================================");
				System.out.printf("\n\n\n");
				
				break;
			case FoundCard:
				System.out.printf("Found the %s card!\n", ((GameObject)args[0]).GetName());
				break;
			case NoCards:
				System.out.printf("Looks like no one has that card. Hmm..\n");
				break;
			case Winner:
				System.out.println("================================================");
				System.out.printf("The winner is [Player %d]: %s\n", args[0], ((GameObject)args[1]).GetName());
				System.out.println("================================================");

				break;
				
			case Loser:
				System.out.println("================================================");
				System.out.printf("The loser is [Player %d]: %s\n", args[0], ((GameObject)args[1]).GetName());
				System.out.println("================================================");

				break;	
		}
	}
	
	public Character ChooseCharacter(int playerIndex) {
		int choice = -1;
		Character retVal = null;
		
		while(choice < 0)
		{
			System.out.printf("[Player %d] Select a character:\n\n", playerIndex);
			for(int i = 0; i < Character.characters.length; i++)
			{
				if(!Character.characters[i].isChosen)
					System.out.printf("[%d]\t%s\n", i+1, Character.characters[i].GetName());
			}
			
			choice = input.nextInt()-1;
			
			if(choice < Character.characters.length && choice >= 0)
			{
				if(!Character.characters[choice].isChosen)
				{
					Character.characters[choice].isChosen = true;
					retVal = Character.characters[choice];
				}
				else
				{
					System.out.println("Character already chosen");
					choice = -1;
				}
			}
			else
			{
				System.out.println("Please enter a valid selection.");
				choice = -1;
			}
		}
		return retVal;
	}
	
	public void SetTurn(int playerIndex, Player p, Board b)
	{
		System.out.printf("\n\n\n\n");
		System.out.println("==================================================================");
		System.out.printf("It's now [Player %d] %s's turn! You're in the %s\n", playerIndex, p.GetCharacter().GetName(), p.FindOnBoard(b).GetName());
	}
	
	public void SetPosition(BoardTile bt)
	{
		// Useless in this implementation of CluedoUI
		// System.out.printf(bt.GetName() + "\n");
	}
	
	public void SetRoll(int d1, int d2)
	{
		System.out.printf("\nRolling, rolling, rolling and you rolled %d and %d giving you a %d\n", d1, d2, d1+d2);
	}
	
	public void WaitAction(String msg)
	{
		System.out.printf(msg + "\n\t[Press enter to continue]\n\n");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Make this ignore *more* silently
		}
	}
	
	public Guess GetGuess(Player p)
	{
		Guess newGuess = new Guess();
		newGuess.room = null;
		
		int choice = -1;
		
		System.out.printf("\n\nWhat would you like to guess the weapon was?\n");
		
		while(choice < 0)
		{
			for(int i = 0; i < Weapon.weapons.length; i++)
			{
				String seenCard = " ";
				if(p.cards.contains(Weapon.weapons[i]))
					seenCard = "X";
				System.out.printf("%d) [%s] %s\n", i+1, seenCard, Weapon.weapons[i].GetName());
			}
		 	System.out.printf("Choose weapon: ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < Weapon.weapons.length)
		 		newGuess.wep = Weapon.weapons[choice];
		 	else
		 	{
		 		System.out.printf("You need to enter a valid selection\n");
		 		choice = -1;
		 	}

		}

		choice = -1;

		System.out.printf("\n\nWho would you like to guess the kidnapper is?\n");
		
		while(choice < 0)
		{
			for(int i = 0; i < Character.characters.length; i++)
			{
				String seenCard = " ";
				if(p.cards.contains(Character.characters[i]))
					seenCard = "X";
				System.out.printf("%d) [%s] %s\n", i+1, seenCard, Character.characters[i].GetName());
			}
		 	System.out.printf("Choose character: ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < Character.characters.length)
		 		newGuess.murderer = Character.characters[choice];
		 	else
		 	{
		 		System.out.printf("\nYou need to enter a valid selection\n");
		 		choice = -1;
		 	}
		}

		choice = -1;
		
		return newGuess;
	}
	
	public Guess GetAccusation(Player p)
	{
		System.out.printf("\n\nThis is an accusation!! Who do you think kidnapped the Millionaire Mogul?\n");
		Guess gn = this.GetGuess(p);
		
		int choice = -1;

		System.out.printf("\nWhich room was the kidnapping committed in?\n");
		
		Room[] roomList = (Room[]) Room.rooms.values().toArray(new Room[Room.rooms.size()]);
		
		while(choice < 0)
		{
			for(int i = 0; i < roomList.length; i++)
			{
				String seenCard = " ";
				if(p.cards.contains(roomList[i]))
					seenCard = "X";
				System.out.printf("%d) [%s] %s\n", i+1, seenCard, roomList[i].GetName());
			}
		 	System.out.printf("\nWhich room do you think it was? ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < roomList.length)
		 		gn.room = roomList[choice];
		 	else
		 	{
		 		System.out.printf("You need to enter a valid selection\n");
		 		choice = -1;
		 	}
		}
		
		return gn;
	}

	@Override
	public Movement PresentMovements(Player p, Movement[] m, int totalDice) {
		int choice = -1;

		System.out.printf("Where would you like to move too?\n");
		
		while(true)
		{
			int i = 0;
			for(i = 0; i < m.length; i++)
			{
				if(m[i].stepsRequired > totalDice)
					System.out.printf("%d) Move %d steps towards %s which is %d steps away\n", i+1, totalDice, m[i].finalRoom.GetName(), m[i].stepsRequired);
				else
					System.out.printf("%d) Move to %s\n", i+1, m[i].finalRoom.GetName());
			}
			
		 	System.out.printf("Which move would you like to make? ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < m.length)
		 		return m[choice];
		 	else
		 	{
		 		System.out.printf("You need to enter a valid selection");
		 	}
		}
	}
}