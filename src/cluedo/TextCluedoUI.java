package cluedo;

import java.io.IOException;
import java.util.Scanner;

public class TextCluedoUI implements CluedoUI {

	Scanner input = new Scanner(System.in);
	
	@Override
	public void DisplayMessage(CluedoMessage msg, Object... args) {
		switch(msg)
		{
			case WelcomeMessage:
				System.out.println("Welcome to Cluedo");
				break;
		}
	}
	
	public Character ChooseCharacter(int playerIndex) {
		int choice = -1;
		Character retVal = null;
		
		while(choice < 0)
		{
			System.out.printf("[Player %d] Select a character:\n", playerIndex);
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
		System.out.printf("It's now [Player %d] %s's turn! You're in the %s\n", playerIndex, p.GetCharacter().GetName(), p.FindOnBoard(b).GetName());
	}
	
	public void SetPosition(BoardTile bt)
	{
		// Useless in this implementation of CluedoUI
		// System.out.printf(bt.GetName() + "\n");
	}
	
	public void SetRoll(int d1, int d2)
	{
		System.out.printf("Rolling, rolling, rolling and you rolled %d and %d giving you a %d\n", d1, d2, d1+d2);
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
		newGuess.room = Room.rooms.get("Spa");
		
		int choice = -1;
		
		System.out.printf("What do you think the murder weapon was?\n");
		
		while(choice < 0)
		{
			for(int i = 0; i < Weapon.weapons.length; i++)
			{
				System.out.printf("%d) [ ] %s\n", i+1, Weapon.weapons[i].GetName());
			}
		 	System.out.printf("Which weapon do you think it was? ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < Weapon.weapons.length)
		 		newGuess.wep = Weapon.weapons[choice];
		 	else
		 	{
		 		System.out.printf("You need to enter a valid selection");
		 		choice = -1;
		 	}

		}

		choice = -1;

		System.out.printf("Who do you think the murder is?\n");
		
		while(choice < 0)
		{
			for(int i = 0; i < Character.characters.length; i++)
			{
				System.out.printf("%d) [ ] %s\n", i+1, Character.characters[i].GetName());
			}
		 	System.out.printf("Which character do you think it was? ");

		 	choice = input.nextInt() - 1;
		 	if(choice >= 0 && choice < Character.characters.length)
		 		newGuess.murderer = Character.characters[choice];
		 	else
		 	{
		 		System.out.printf("You need to enter a valid selection");
		 		choice = -1;
		 	}
		}

		choice = -1;
		
		return newGuess;
	}
}
