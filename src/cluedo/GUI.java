package cluedo;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GUI extends JFrame implements CluedoUI {



	private JFrame frame;
	private JComponent board;
	private JPanel controls; 
	private JPanel diceControl; 
	private JLabel diceHolder1 = new JLabel();
	private JLabel diceHolder2= new JLabel();
	private JMenu jMenu1;
	private JMenu jMenu2;
	private JMenuBar jMenuBar1;
	private GUICanvas canvas;

	private ImageIcon dice1 = makeImageIcon("src/resources/dice1.jpg");
	private ImageIcon dice2 = makeImageIcon("src/resources/dice2.jpg");
	private ImageIcon dice3 = makeImageIcon("src/resources/dice3.jpg");
	private ImageIcon dice4 = makeImageIcon("src/resources/dice4.jpg");
	private ImageIcon dice5 = makeImageIcon("src/resources/dice5.jpg");
	private ImageIcon dice6 = makeImageIcon("src/resources/dice6.jpg");

	private JButton rollDice = new JButton("Roll Dice");

	public GUI()  {
		setupFrame();
	}
	
	private ImageIcon makeImageIcon(String filename)
	{		
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = GUI.class.getResource(filename);

		ImageIcon icon = null;
		if (imageURL != null) {
			icon = new ImageIcon(imageURL);
		}
		return icon;
	}	

	public void setDice(int first, int second)
	{
		switch(first)
		{
			case 1: diceHolder1.setIcon(dice1);
			case 2: diceHolder1.setIcon(dice2);
			case 3: diceHolder1.setIcon(dice3);
			case 4: diceHolder1.setIcon(dice4);
			case 5: diceHolder1.setIcon(dice5);;
			default: diceHolder1.setIcon(dice6);
			
		}
		
		switch(second)
		{
			case 1: diceHolder2.setIcon(dice1);
			case 2: diceHolder2.setIcon(dice2);
			case 3: diceHolder2.setIcon(dice3);
			case 4: diceHolder2.setIcon(dice4);
			case 5: diceHolder2.setIcon(dice5);
			default: diceHolder2.setIcon(dice6);		
		}
	}


	public void setupFrame(){
		frame = new JFrame();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Game");
		jMenuBar1.add(jMenu2);

		frame.setJMenuBar(jMenuBar1);
		
		frame.setPreferredSize(new Dimension(600,750));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controls = new JPanel();
			
		canvas = new GUICanvas();
		controls.setLayout(new BorderLayout());
		
		canvas.setSize(600, 600);
		controls.add(canvas, BorderLayout.CENTER);


		diceControl = new JPanel();
		diceControl.setLayout(new GridLayout(0,1));
		diceControl.add(rollDice);

		setDice(1,1);

		diceControl.add(diceHolder1);
		diceControl.add(diceHolder2);

		controls.add(diceControl,BorderLayout.SOUTH);

		frame.add(controls);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	@Override
	public int GetNumPlayers() {
		Object[] options = {"3", "4", "5", "6"};

		String reply = (String)JOptionPane.showInputDialog(null, "How many players?", "Number of players", JOptionPane.PLAIN_MESSAGE, null, options, "ham");
		int numPlayers = 0;
		try
		{
			numPlayers = Integer.parseInt(reply);
		}
		catch(NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(null,  "Please select the number of players");
		}
		return numPlayers;
	}


	@Override
	public void DisplayMessage(CluedoMessage msg, Object... args) {
		switch(msg)
		{
			case WelcomeMessage:
				JOptionPane.showMessageDialog(null,  "Welcome to CLUEDO!\nGame designed by Ben Anderson and Patrick Barnes");
				
				break;
			case FoundCard:
				JOptionPane.showMessageDialog(null,  "Found the "+((GameObject)args[0]).GetName()+" card!\n");
				break;
			case NoCards:
				JOptionPane.showMessageDialog(null,  "Looks like no one has that card. Hmm...");
				break;
			case Winner:
				
				JOptionPane.showMessageDialog(null,  "The winner is [Player "+args[0]+"]: "+((GameObject)args[1]).GetName());
				break;
				
			case Loser:
				JOptionPane.showMessageDialog(null,  "The loser is [Player "+args[0]+"]: "+((GameObject)args[1]).GetName());
				break;	
		}

	}


	@Override
	public Character ChooseCharacter(int playerIndex) {

		int numChars = 0;
		for(Character c :  Character.characters)
			if(!c.isChosen) numChars++;


		String[] charactersToShow = new String[numChars];

		int i = 0;
		for(Character c :  Character.characters){
			if(!c.isChosen){
				charactersToShow[i] = c.GetName();
				i++;
			}
		}

		String reply = (String)JOptionPane.showInputDialog(null, "Player " + playerIndex + ", please select character to play as", "Player "+ playerIndex +" Character Selection", JOptionPane.PLAIN_MESSAGE, null, charactersToShow, Character.characters[0]);

		for(Character c: Character.characters){
			if(reply.equals(c.GetName())) {
				c.isChosen = true;
				return c;
			}
		}



		return null;
	}


	@Override
	public void SetTurn(int playerIndex, Player p, Board b) {
		// TODO Auto-generated method stub

	}


	@Override
	public void SetPosition(BoardTile bt) {
		// TODO Auto-generated method stub

	}


	@Override
	public void SetRoll(int d1, int d2) {
		// TODO Auto-generated method stub

	}


	@Override
	public void WaitAction(String msg) {
		// TODO Auto-generated method stub

	}


	@Override
	public Guess GetGuess(Player p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Guess GetAccusation(Player p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Movement PresentMovements(Player p, Movement[] m, int diceTotal) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void NotifyMoved(int nSteps, Room r) {
		// TODO Auto-generated method stub

	}	
}
