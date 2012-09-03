import cluedo.*;

public class Assign2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI ui = new GUI();
		
		int numPlayers = ui.GetNumPlayers();
		
		
		Game cluedo = new Game(numPlayers, ui);
		
		try 
		{
			cluedo.Start();
		}
		catch(CluedoException ce)
		{
			System.out.println(ce.toString());
		}
	}

}
