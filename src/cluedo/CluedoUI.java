package cluedo;

enum CluedoMessage
{
	WelcomeMessage
};

public interface CluedoUI {
	public void DisplayMessage(CluedoMessage msg, Object... args);
	public Character ChooseCharacter(int playerIndex);
	public void SetTurn(int playerIndex, Player p);
}

