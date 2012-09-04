package cluedo;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Player {

	private static Random random = new Random();
	
	private Character playerChar;
	private Board gameBoard;
	public Guess myGuess = null;
	public CountDownLatch waitLatch = new CountDownLatch(1);
	
	List<GameObject> cards = new ArrayList<GameObject>();
	
	public Character GetCharacter() {
		return this.playerChar;
	}
	
	public void SetPosition(int x, int y)
	{
		this.playerChar.X = x;
		this.playerChar.Y = y;
	}
	
	public BoardTile FindOnBoard(Board b)
	{
		BoardTile bt = b.boardSpaces[this.playerChar.X][this.playerChar.Y];
		if(bt == null)
			bt = BoardTile.Empty;
		
		return bt;
	}
	
	public void SetCharacter(Character selectCharacter) {
		this.playerChar = selectCharacter;
	}
	
	public void TakeTurn(CluedoUI ui, Board b)
	{
		ui.SetPosition(this.FindOnBoard(b));
		int diceOne = Player.random.nextInt(6)+1;
		int diceTwo = Player.random.nextInt(6)+1;
		this.gameBoard = b;
		ui.SetRoll(diceOne, diceTwo);
		this.waitLatch = new CountDownLatch(1);
		
		// DEBUG: BABAP
		//b.PrintBoard();
		
		Movement[] movements = this.PlotMovements(b);
		int[][] moveables = this.PlotMoveables(b, diceOne + diceTwo);
		ui.PresentMovements(this, movements, moveables, diceOne + diceTwo);
	}
		
	public void CompleteTurn(int x, int y, CluedoUI ui)
	{
		
		/*
		if(chosenMovement == null) return null;
		
		if(chosenMovement.stepsRequired > diceTotal)
		{
			Pair stepTarget = chosenMovement.steps.get(diceTotal - 1);
			this.playerChar.X = stepTarget.getFirst();
			this.playerChar.Y = stepTarget.getSecond();
			ui.NotifyMoved(chosenMovement.stepsRequired - diceTotal, chosenMovement.finalRoom);
		}
		else
		{
			this.playerChar.X = chosenMovement.finalX;
			this.playerChar.Y = chosenMovement.finalY;
		}
		*/
		
		System.out.println("Moving to " + x + " " + y);
		this.SetPosition(x, y);
		ui.Repaint();
		
		
		Room tRoom = null;
		BoardTile tile = this.FindOnBoard(gameBoard);
		if(tile instanceof Room && tile != Room.Empty && tile != null)
		{		
			tRoom = (Room)tile;
		}
		else if(tile instanceof Door)
		{
			tRoom = ((Door)tile).linkedRoom;
		}
		
		
		Guess newGuess = null;
		if(tRoom != null)
		{
			if(tRoom == Room.rooms.get("Pool"))
			{
				newGuess = ui.GetAccusation(this);
				newGuess.isAccusation = true;
			}
			else
			{
				newGuess = ui.GetGuess(this);
				newGuess.room = tRoom;
				newGuess.Print();
			}
		}
		
		this.myGuess = newGuess;
		
		this.waitLatch.countDown();
	}
	
	public int[][] PlotMoveables(Board b, int roll)
	{
		Queue<PathfindFrame> toTest = new LinkedList<PathfindFrame>();
		toTest.add(new PathfindFrame(new ArrayList<Pair>(), this.playerChar.X, this.playerChar.Y));
		
		int[][] moveable = new int[Board.Width][Board.Height];
		boolean[] tested = new boolean[Board.Width * Board.Height];
		List<Room> rooms = new LinkedList<Room>();
		
		PathfindFrame curFrame = null;
		while(!toTest.isEmpty())
		{
			curFrame = toTest.remove();
			
			if(curFrame.curPosX >= Board.Width || curFrame.curPosY >= Board.Height) continue; 
			if(tested[(curFrame.curPosY * Board.Width) + curFrame.curPosX]) continue;
			tested[(curFrame.curPosY * Board.Width) + curFrame.curPosX] = true;
			
			//if(b.boardSpaces[curFrame.curPosX][curFrame.curPosY] instanceof Room)
			//	curFrame.numSteps--;
			
			System.out.printf("Examining %d, %d. numSteps=%d. curRoll=%d\n", curFrame.curPosX, curFrame.curPosY, curFrame.numSteps, roll);
			if(curFrame.numSteps > roll) continue;
			else
			{
				PathfindFrame leftFrame = new PathfindFrame(curFrame.steps, curFrame.curPosX-1, curFrame.curPosY);
				PathfindFrame rightFrame = new PathfindFrame(curFrame.steps, curFrame.curPosX+1, curFrame.curPosY);
				PathfindFrame upFrame = new PathfindFrame(curFrame.steps, curFrame.curPosX, curFrame.curPosY-1);
				PathfindFrame downFrame = new PathfindFrame(curFrame.steps, curFrame.curPosX, curFrame.curPosY+1);
				leftFrame.numSteps = curFrame.numSteps + 1;
				rightFrame.numSteps= curFrame.numSteps + 1;
				upFrame.numSteps= curFrame.numSteps + 1;
				downFrame.numSteps= curFrame.numSteps + 1;
				
				int left = (curFrame.curPosY * Board.Width) + curFrame.curPosX-1;
				int right = (curFrame.curPosY * Board.Width) + curFrame.curPosX+1;
				int up = ((curFrame.curPosY-1) * Board.Width) + curFrame.curPosX;
				int down = ((curFrame.curPosY+1) * Board.Width) + curFrame.curPosX;
				
				if(curFrame.curPosX > 0 && left > 0 && !tested[left])
					toTest.add(leftFrame);
				if(curFrame.curPosX < Board.Width && right > 0 && right < tested.length && !tested[right])
					toTest.add(rightFrame);
				if(curFrame.curPosY > 0 && up > 0 && !tested[up])
					toTest.add(upFrame);
				if(curFrame.curPosY < Board.Height && down > 0 && down < tested.length && !tested[down])
					toTest.add(downFrame);
				
				moveable[curFrame.curPosX][curFrame.curPosY] = 1;

				if(b.boardSpaces[curFrame.curPosX][curFrame.curPosY] instanceof Door)
				{
					System.out.println("Is a room!");
					moveable[curFrame.curPosX][curFrame.curPosY] = 2;
					rooms.add(((Door)b.boardSpaces[curFrame.curPosX][curFrame.curPosY]).linkedRoom);
				}
				else if(b.boardSpaces[curFrame.curPosX][curFrame.curPosY] instanceof Room)
					moveable[curFrame.curPosX][curFrame.curPosY] = 0;
			}
			
		}
		
		for(int x = 0; x < Board.Width; x++)
		{
			for(int y = 0; y < Board.Height; y++)
			{
				for(Room r : rooms)
				{
					if(r.equals(b.boardSpaces[x][y]))
						moveable[x][y] = 2;
				}		
			}
		}
		
		return moveable;
	}
	
	public Movement[] PlotMovements(Board board)
	{
		Boolean[] tested = new Boolean[board.Width * board.Height];
		for(int i = 0; i < board.Width * board.Height; i++)
			tested[i] = false;
		
		Queue<PathfindFrame> toTest = new LinkedList<PathfindFrame>();
		
		toTest.add(new PathfindFrame(new ArrayList<Pair>(), this.playerChar.X, this.playerChar.Y));
		
		PathfindFrame curFrame = null;
		while(!toTest.isEmpty())
		{
			curFrame = toTest.remove();
						
			int self = (curFrame.curPosY * board.Width) + curFrame.curPosX;
			if(tested[self])
				continue;
			else
				tested[self] = true;			
			
				//System.out.printf("Started processing %d, %d with %d steps:", curFrame.curPosX, curFrame.curPosY, curFrame.steps.size());
			if(board.boardSpaces[curFrame.curPosX][curFrame.curPosY] instanceof Door || board.boardSpaces[curFrame.curPosX][curFrame.curPosY] == null)
			{
				//if(board.boardSpaces[curFrame.curPosX][curFrame.curPosY] != null) System.out.printf("%s", board.boardSpaces[curFrame.curPosX][curFrame.curPosY].getClass().getName());
				//else System.out.printf("null");
				
			}
			else
			{
				continue;
			}
			//System.out.println();
			
			
			
			
			curFrame.steps.add(new Pair(curFrame.curPosX, curFrame.curPosY));
			
			int left = (curFrame.curPosY * board.Width) + curFrame.curPosX-1;
			int right = (curFrame.curPosY * board.Width) + curFrame.curPosX+1;
			int up = ((curFrame.curPosY-1) * board.Width) + curFrame.curPosX;
			int down = ((curFrame.curPosY+1) * board.Width) + curFrame.curPosX;
			
			if(curFrame.curPosX < board.Width-1 && !tested[right])
			{
				toTest.add(new PathfindFrame(curFrame.steps, curFrame.curPosX+1, curFrame.curPosY));
				//System.out.printf("Moved right\n");
			}
			if(curFrame.curPosX > 0 && !tested[left])
			{
				toTest.add(new PathfindFrame(curFrame.steps, curFrame.curPosX-1, curFrame.curPosY));
				//System.out.printf("Moved left\n");
			}
			if(curFrame.curPosY < board.Height-1 && !tested[down])
			{
				toTest.add(new PathfindFrame(curFrame.steps, curFrame.curPosX, curFrame.curPosY+1));
				//System.out.printf("Moved down\n");
			}
			if(curFrame.curPosY > 0 && !tested[up])
			{
				toTest.add(new PathfindFrame(curFrame.steps, curFrame.curPosX, curFrame.curPosY-1));
				//System.out.printf("Moved up\n");
			}
				
			if(board.boardSpaces[curFrame.curPosX][curFrame.curPosY] instanceof Door)
			{
				Door d = (Door)board.boardSpaces[curFrame.curPosX][curFrame.curPosY];
				if(d.linkedRoom.tempBest.size() > curFrame.steps.size() || d.linkedRoom.tempBest.isEmpty()) 
				{
					//System.out.printf("currently %d is better than %d for %s\n", curFrame.steps.size(), d.linkedRoom.tempBest.size(), d.linkedRoom.GetName());
					d.linkedRoom.tempBest.clear();
					d.linkedRoom.tempBest = curFrame.steps;
				}
			}
			
			
		}
		
		Movement[] moves = new Movement[Room.rooms.size()];
		for(int i = 0; i < Room.rooms.values().size(); i++)
		{
			Room fr = ((Room)Room.rooms.values().toArray()[i]);
			Movement m = new Movement();
			//System.out.printf("Testing for %s. size=%d\n", fr.GetName(), fr.tempBest.size());
			Pair finalStep = fr.tempBest.get(fr.tempBest.size() -1);
			m.finalX = finalStep.getFirst();
			m.finalY = finalStep.getSecond();
			m.stepsRequired = fr.tempBest.size();
			m.finalRoom = fr;
			m.steps = fr.tempBest;
			moves[i] = m;
		}
		return moves;
	}
	
	public void GiveCard(GameObject card)
	{
		cards.add(card);
	}

}
