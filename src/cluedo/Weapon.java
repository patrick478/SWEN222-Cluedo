package cluedo;

public class Weapon extends GameObject{
	public Weapon(String n) {
		this.SetName(n);
	}
	
	public static final Weapon[] weapons = new Weapon[] {
		new Weapon("Rope"), 
		new Weapon("Candlestick")
		//TODO ADD REST OF CHARACTERS
	};
}
