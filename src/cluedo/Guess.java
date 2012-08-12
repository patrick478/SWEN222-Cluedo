package cluedo;

public class Guess {
	public Room room;
	public Character killer;
	public Weapon wep;
	
	public static Guess GenerateRandomGuess()
	{
		return new Guess();
	}
}
