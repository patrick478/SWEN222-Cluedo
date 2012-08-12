package cluedo;

public class Character extends GameObject {
	
	int startPosX = -1;
	int startPosY = -1;
	public boolean isChosen;
	
	public Character(String n, int x, int y) {
		this.SetName(n);
		this.startPosX = x;
		this.startPosY = y;
	}  

	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet", 0, 0), 
		new Character("Jack Mustard", 0, 0),
		new Character("Diane White", 0, 0), 
		new Character("Jacob Green", 0, 0), 
		new Character("Eleanor Peacock", 6, 0), 
		new Character("Victor Plum", 0, 0)
	};
}
