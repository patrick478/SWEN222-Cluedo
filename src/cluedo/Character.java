package cluedo;

import java.util.*;

public class Character extends GameObject {
	public Character(String n) {
		this.SetName(n);
	}

	public static Character SelectCharacter() {
		int choice = -1;
		Character retVal = null;
		Scanner input = new Scanner(System.in);
		//TODO: Make it so two people cannot select the same character
		while(choice < 0)
		{
			System.out.printf("Select a character:\n");
			for(int i = 0; i < characters.length; i++)
				if(characterChosen[i]==false)
					System.out.printf("[%d]\t%s\n", i+1, characters[i].GetName());
			while(input.hasNextInt())
				choice = input.nextInt()-1;
			if(choice < characters.length){
				if(characterChosen[choice]==false){
					characterChosen[choice] = true;
					retVal = characters[choice];
				}
				else{ System.out.println("Character already chosen");
				choice = -1;
				}

			}
			else
				choice = -1;
		}
		return retVal;
	}

	public static Boolean[] characterChosen = {false, false, false, false, false, false};

	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet"), 
		new Character("Jack Mustard"),
		new Character("Diane White"), 
		new Character("Jacob Green"), 
		new Character("Eleanor Peacock"), 
		new Character("Victor Plum")
	};
}
