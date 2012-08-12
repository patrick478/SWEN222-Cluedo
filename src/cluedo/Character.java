package cluedo;

public class Character extends GameObject {
	
	int startPosX = -1;
	int startPosY = -1;
	public Character(String n, int x, int y) {
		this.SetName(n);
		this.startPosX = x;
		this.startPosY = y;
	}
	
	public boolean isChosen = false;

	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet"), 
		new Character("Jack Mustard"),
		new Character("Diane White"), 
		new Character("Jacob Green"), 
		new Character("Eleanor Peacock", 6, 0), 
		new Character("Victor Plum")
	};
}
