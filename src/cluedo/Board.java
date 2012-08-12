package cluedo;

public class Board {
	public final int Width = 24;
	public final int Height = 29;
	public BoardTile[][] boardSpaces = new BoardTile[Width][Height];
	
	public void Setup()
	{
		Room.rooms.put("Dining Room", new Room("Dining Room"));
		Room.rooms.put("Kitchen", new Room("Kitchen"));
		Room.rooms.put("Spa", new Room("Spa"));
		Room.rooms.put("Living Room", new Room("Living Room"));
		Room.rooms.put("Observatory", new Room("Observatory"));
		Room.rooms.put("Theatre", new Room("Theatre"));
		Room.rooms.put("Patio", new Room("Patio"));
		Room.rooms.put("Pool", new Room("Pool"));
		Room.rooms.put("Hall", new Room("Hall"));
		Room.rooms.put("Kitchen", new Room("Kitchen"));
		Room.rooms.put("Guest House", new Room("Guest House"));


		
		//Spa
		this.SetRegion(0, 0, 5, 5, Room.rooms.get("Spa"));
		this.SetRegion(0, 6, 4, 7, Room.rooms.get("Spa"));
		this.SetDoor(5, 6, new Door(Room.rooms.get("Spa")));
		
		
		//Kitchen
		this.SetRegion(0, 21, 5, 21 , Room.rooms.get("Kitchen"));
		this.SetRegion(0, 22, 6, 28, Room.rooms.get("Kitchen"));
		this.SetDoor(6, 21, new Door(Room.rooms.get("Kitchen")));

		//Theatre
		this.SetRegion(8, 0, 12, 7 , Room.rooms.get("Theatre"));
		this.SetDoor(10, 8, new Door(Room.rooms.get("Theatre")));
		
		//Living Room
		this.SetRegion(14, 0, 19, 7 , Room.rooms.get("Living Room"));
		this.SetRegion(15, 8, 17, 8, Room.rooms.get("Living Room"));
		this.SetDoor(16, 9, new Door(Room.rooms.get("Living Room")));
		
		//Observatory
		this.SetRegion(22, 0, 23, 8 , Room.rooms.get("Observatory"));
		this.SetDoor(21, 8, new Door(Room.rooms.get("Observatory")));
		
		//Patio
		this.SetRegion(0, 10, 3, 18, Room.rooms.get("Patio"));
		this.SetRegion(4, 11, 7, 17, Room.rooms.get("Patio"));
		this.SetDoor(5, 10, new Door(Room.rooms.get("Patio")));
		this.SetDoor(8, 12, new Door(Room.rooms.get("Patio")));
		this.SetDoor(8, 16, new Door(Room.rooms.get("Patio")));
		this.SetDoor(5, 18, new Door(Room.rooms.get("Patio")));

		//Pool
		this.SetRegion(10, 11, 17, 16, Room.rooms.get("Pool"));
		this.SetDoor(14, 10, new Door(Room.rooms.get("Pool")));
		this.SetDoor(9, 12, new Door(Room.rooms.get("Pool")));
		this.SetDoor(9, 13, new Door(Room.rooms.get("Pool")));
		this.SetDoor(9, 14, new Door(Room.rooms.get("Pool")));
		this.SetDoor(9, 15, new Door(Room.rooms.get("Pool")));
		this.SetDoor(11, 17, new Door(Room.rooms.get("Pool")));
		this.SetDoor(17, 17, new Door(Room.rooms.get("Pool")));

		
		 //Hall
		this.SetRegion(19, 11, 23, 17, Room.rooms.get("Hall"));
		this.SetDoor(18, 13, new Door(Room.rooms.get("Hall")));
		this.SetDoor(18, 14, new Door(Room.rooms.get("Hall")));		
		this.SetDoor(22, 10, new Door(Room.rooms.get("Hall")));


		//Kitchen
		this.SetRegion(0, 21, 5, 21, Room.rooms.get("Kitchen"));
		this.SetRegion(0, 22, 6, 28, Room.rooms.get("Kitchen"));
		this.SetDoor(6, 21, new Door(Room.rooms.get("Kitchen")));
		
		//Dining Room
		this.SetRegion(10, 19, 15, 22, Room.rooms.get("Dining Room"));
		this.SetRegion(9, 23, 16, 28, Room.rooms.get("Dining Room"));
		this.SetDoor(12, 18, new Door(Room.rooms.get("Dining Room")));
		this.SetDoor(16, 21, new Door(Room.rooms.get("Dining Room")));

		//Guest House
		this.SetRegion(20, 21, 23, 28, Room.rooms.get("Guest House"));
		this.SetRegion(21, 20, 23, 20, Room.rooms.get("Guest House"));
		this.SetDoor(20, 20, new Door(Room.rooms.get("Guest House")));
		
		
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
		for(int i = 0; i < Height; i++) {
			for(int j = 0; j < Width; j++) {
				if(this.boardSpaces[j][i] != null)
					System.out.printf(" %c", this.boardSpaces[j][i].ShortName());
				else
					System.out.printf(" .");
			}
			System.out.println();
		}
	}
}