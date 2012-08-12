package cluedo;

public class Guess {
	public Room room;
	public Character murderer;
	public Weapon wep;
	
	boolean isAccusation = false;
	
	public Guess(Room r, Character c, Weapon w)
	{
		this.room = r;
		this.murderer = c;
		this.wep = w;
	}
	
	public Guess()
	{
		this.room = null;
		this.murderer = null;
		this.wep = null;
	}
	
	public boolean isValid()
	{
		return (this.room != null && this.murderer != null && this.wep != null);
	}
	
	public void Print()
	{
		if(this.isValid())
			System.out.printf("%s in the %s with the %s\n", this.murderer.GetName(), this.room.GetName(), this.wep.GetName());
		else
			System.out.printf("[BUG!] (This shouldn't be possible) Invalid guess - uh oh!!");
	}
}
