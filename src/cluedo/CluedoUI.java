package cluedo;

enum CluedoMessage
{
	WelcomeMessage
};

public interface CluedoUI {
	public void DisplayMessage(CluedoMessage msg, Object... args);
}
