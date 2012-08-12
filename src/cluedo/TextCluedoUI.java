package cluedo;

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
	
	public void SetTurn(int playerIndex, Player p)
	{
		System.out.printf("It's now [Player %d] %s's turn!\n", playerIndex, p.GetCharacter().GetName());
	}
}
