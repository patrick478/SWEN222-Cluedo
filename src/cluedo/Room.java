package cluedo;

import java.util.*;

public class Room extends BoardTile {
	
	public Room(String n)
	{
		this.SetName(n);
	}
	
	public static HashMap<String, Room> rooms = new HashMap<String, Room>();
}