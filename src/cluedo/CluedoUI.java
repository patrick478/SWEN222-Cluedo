package cluedo;

enum CluedoMessage
{
	WelcomeMessage,
	FoundCard,
	NoCards,
	Winner,
	Loser
};

public interface CluedoUI {
	public int GetNumPlayers();
	public void DisplayMessage(CluedoMessage msg, Object... args);
	public Character ChooseCharacter(int playerIndex);
	public void SetTurn(int playerIndex, Player p, Board b);
	public void SetPosition(BoardTile bt);
	public void SetRoll(int d1, int d2);
	public void WaitAction(String msg);
	public Guess GetGuess(Player p);
	public Guess GetAccusation(Player p);
	public void PresentMovements(Player p, Movement[] m, int diceTotal);
	public void NotifyMoved(int nSteps, Room r);
	
	public void Repaint();
}

