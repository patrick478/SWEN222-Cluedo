package cluedo;

import java.util.*;

public class Player {

	private static Random random = new Random();
	
	private Character playerChar;
	
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
	
	public Guess TakeTurn(CluedoUI ui, Board b)
	{
		ui.SetPosition(this.FindOnBoard(b));
		int diceOne = Player.random.nextInt(6)+1;
		int diceTwo = Player.random.nextInt(6)+1;
		
		ui.SetRoll(diceOne, diceTwo);
		
		// DEBUG: BABAP
		//b.PrintBoard();
		
		Movement[] movements = this.PlotMovements(b);
		Movement chosenMovement = ui.PresentMovements(this, movements, diceOne + diceTwo);
		if(chosenMovement == null) return null;
		
		if(chosenMovement.stepsRequired > diceOne + diceTwo)
		{
			Pair stepTarget = chosenMovement.steps.get((diceOne + diceTwo - 1));
			this.playerChar.X = stepTarget.getFirst();
			this.playerChar.Y = stepTarget.getSecond();
			ui.NotifyMoved(chosenMovement.stepsRequired - (diceOne + diceTwo), chosenMovement.finalRoom);
		}
		else
		{
			this.playerChar.X = chosenMovement.finalX;
			this.playerChar.Y = chosenMovement.finalY;
		}
		
		
		Room tRoom = null;
		BoardTile tile = this.FindOnBoard(b);
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
		
		return newGuess;
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
