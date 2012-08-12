package cluedo;

public class Board {
	public BoardTile[][] boardSpaces = new BoardTile[24][29];
	
	public void Setup()
	{
		Room.rooms.put("Dining Room", new Room("Dining Room"));
		Room.rooms.put("Kitchen", new Room("Kitchen"));
		Room.rooms.put("Spa", new Room("Spa"));
		
		this.SetRegion(0, 0, 5, 5, Room.rooms.get("Spa"));
		this.SetRegion(0, 6, 4, 7, Room.rooms.get("Spa"));
		this.SetDoor(5, 6, new Door(Room.rooms.get("Spa")));
		
		this.SetRegion(0, 21, 5, 21 , Room.rooms.get("Kitchen"));
		this.SetRegion(0, 22, 6, 28, Room.rooms.get("Kitchen"));
		this.SetDoor(6, 21, new Door(Room.rooms.get("Kitchen")));

	}
	
	private void SetDoor(int x, int y, Door d)
	{
		this.boardSpaces[x][y] = d;
	}
	
	private void SetRegion(int x1, int y1, int x2, int y2, BoardTile t) {
		for(int i = x1; i <= x2; i++)
		{
			for(int j = y1; j <= y2; j++) {
				this.boardSpaces[i][j] = t;
			}
		}
	}
	
	public void PrintBoard()
	{
		for(int i = 0; i < 29; i++) {
			for(int j = 0; j < 24; j++) {
				if(this.boardSpaces[j][i] != null)
					System.out.printf("%c", this.boardSpaces[j][i].ShortName());
				else
					System.out.printf(".");
			}
			System.out.println();
		}
	}
}