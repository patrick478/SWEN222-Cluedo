package cluedo;

public class Cluedo {
	
	private final int MaxPlayers = 6;
	private int nPlayers = 0;
	
	private CluedoUI uiVendor;
	
	public Cluedo(int numPlayers)
	{
		this.nPlayers = numPlayers;
	}
	
	public void Start() throws CluedoException
	{
		if(this.nPlayers > this.MaxPlayers)
			throw new CluedoException(String.format("The number of players must be less than or equal to %d\n", this.MaxPlayers));
		
		if(this.uiVendor == null)
			this.uiVendor = new TextCluedoUI();
		
		this.uiVendor.DisplayMessage(CluedoMessage.WelcomeMessage);
		
		Board b = new Board();
		b.Setup();
		b.PrintBoard();
	}
}
