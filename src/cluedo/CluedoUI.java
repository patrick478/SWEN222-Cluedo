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
	public void DisplayMessage(CluedoMessage msg, Object... args);
	public Character ChooseCharacter(int playerIndex);
	public void SetTurn(int playerIndex, Player p, Board b);
	public void SetPosition(BoardTile bt);
	public void SetRoll(int d1, int d2);
	public void WaitAction(String msg);
	public Guess GetGuess(Player p);
	public Guess GetAccusation(Player p);
	public Movement PresentMovements(Player p, Movement[] m, int diceTotal);
}

