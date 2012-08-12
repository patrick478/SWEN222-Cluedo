package cluedo;

public class Weapon extends GameObject{
	
	private Room room;
	
	public Weapon(String n) {
		this.SetName(n);
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public static final Weapon[] weapons = new Weapon[] {
		new Weapon("Rope"), 
		new Weapon("Candlestick"),
		new Weapon("Knife"),
		new Weapon("Pistol"),
		new Weapon("Baseball Bat"),
		new Weapon("Dumbbell"),
		new Weapon("Trophy"),
		new Weapon("Poison"),
		new Weapon("Axe")
		
		
		
	};
}