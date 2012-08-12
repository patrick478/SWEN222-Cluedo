package cluedo;

public class Board {
	public BoardTile[][] boardSpaces = new BoardTile[30][30];
	
	public void Setup()
	{
		Room.rooms.put("Dining Room", new Room("Dining Room"));
		Room.rooms.put("Kitchen", new Room("Kitchen"));
		Room.rooms.put("Spa", new Room("Spa"));
		
		this.SetRegion(0, 0, 5, 5, Room.rooms.get("Spa"));
		this.SetRegion(0, 6, 4, 6, Room.rooms.get("Spa"));
		this.SetDoor(5, 4, new Door(Room.rooms.get("Spa")));
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
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				if(this.boardSpaces[j][i] != null)
					System.out.printf("%c", this.boardSpaces[j][i].ShortName());
				else
					System.out.printf(".");
			}
			System.out.println();
		}
	}
}