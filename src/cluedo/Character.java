package cluedo;

public class Character extends GameObject {
	public Character(String n) {
		this.SetName(n);
	}
	
	public boolean isChosen = false;

	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet"), 
		new Character("Jack Mustard"),
		new Character("Diane White"), 
		new Character("Jacob Green"), 
		new Character("Eleanor Peacock"), 
		new Character("Victor Plum")
	};
}
