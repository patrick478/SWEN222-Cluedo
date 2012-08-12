import cluedo.*;

public class Assign1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO MAKE THIS PROPERLY USING CluedoUI
		System.out.println("[DEBUG] TODO Assign1.java:main sets numPlayers = 4");
		int numPlayers = 4;
		
		Game cluedo = new Game(numPlayers);
		
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
