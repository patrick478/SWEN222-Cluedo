package cluedo;

import java.util.*;

public class Character extends GameObject {
	
	int startPosX = -1;
	int startPosY = -1;
	public boolean isChosen;
	public int X;
	public int Y;
	
	public Character(String n, int x, int y) {
		this.SetName(n);
		this.startPosX = x;
		this.startPosY = y;
		this.X = this.startPosX;
		this.Y = this.startPosY;
	}  

	public static final Character[] characters = new Character[] {
		new Character("Kasandra Scarlet", 18, 28), 
		new Character("Jack Mustard", 7, 28),
		new Character("Diane White", 0, 19), 
		new Character("Jacob Green", 0, 9), 
		new Character("Eleanor Peacock", 6, 0), 
		new Character("Victor Plum", 20, 0)
	};
	
	public static Character getRandom()
	{
		Random r = new Random();
		return characters[r.nextInt(characters.length)];
	}
}
