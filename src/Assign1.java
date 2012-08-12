import java.util.*;

import cluedo.*;

public class Assign1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numPlayers = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.printf("Please enter the number of players: ");
		
		numPlayers = scanner.nextInt();
		scanner.close();
		
		Cluedo cluedo = new Cluedo(numPlayers);
		
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
