package cluedo;

public class Door extends BoardTile {
	public Room linkedRoom = null;
	public int tempBest = Integer.MAX_VALUE;
	
	public Door(Room lr) {
		this.linkedRoom = lr;
	}
	
	@Override
	public char ShortName()
	{
		return 'd';
	}
}
