package cluedo;

public class TextCluedoUI implements CluedoUI {

	@Override
	public void DisplayMessage(CluedoMessage msg, Object args) {
		switch(msg)
		{
			case WelcomeMessage:
				System.out.println("Welcome to Cluedo");
				break;
		}
	}

}
