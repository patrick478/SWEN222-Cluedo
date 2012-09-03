import cluedo.*;

public class Assign2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI ui = new GUI();
				
		
		Game cluedo = new Game(0, ui);
		
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
