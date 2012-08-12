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
			System.out.printf("Select a character:");
			for(int i = 0; i < characters.length; i++)
				System.out.printf("[%d]\t%s\n", i+1, characters[i]);
			
			choice = input.nextInt();
			if(choice < characters.length)
				retVal = characters[choice];
			else
				choice = -1;
		}
		
		input.close();
		return retVal;
	}
	
	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet"), 
		new Character("Jack Mustard"),
		new Character("Diane White"), 
		new Character("Jacob Green"), 
		new Character("Eleanor Peacock"), 
		new Character("Victor Plum")
	};
}