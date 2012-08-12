package cluedo;

import java.util.*;

public class Room extends BoardTile {
	
	int tempBest = Integer.MAX_VALUE;
	public Room(String n)
	{
		this.SetName(n);
	}
	
	public static HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	public static Room getRandom()
	{
		Random r = new Random();
		int target = r.nextInt(rooms.size()); 
		return (Room) ((rooms.values().toArray())[target]);
		
		
	}
}