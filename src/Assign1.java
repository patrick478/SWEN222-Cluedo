import cluedo.*;

public class Assign1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CluedoUI ui = new TextCluedoUI();
		
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
