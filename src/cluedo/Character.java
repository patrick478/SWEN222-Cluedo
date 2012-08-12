package cluedo;

public class Character extends GameObject {
	public Character(String n) {
		this.SetName(n);
	}
	
	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet"), 
		new Character("Jack Mustard")
		//TODO ADD REST OF CHARACTERS
	};
}
