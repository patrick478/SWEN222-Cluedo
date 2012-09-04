package cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GUI extends JFrame implements CluedoUI, MouseListener {

	public static double xOffset = 12;
	public static double yOffset = 12;
	public static double xSize = 16.25;
	public static double ySize = 16.44;

	private JFrame frame;
	private JPanel controls; 
	private JPanel diceControl; 
	private JLabel diceHolder1 = new JLabel();
	private JLabel diceHolder2= new JLabel();
	
	private JLabel turnText;
	private JLabel characterText;
	private JLabel instructionsText;
	
	private JButton cardsButton = new JButton("Cards");
	private JButton checklistButton = new JButton("Checklist");
	
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

	private JLabel rollDice = new JLabel("  You rolled:");
	public boolean chooseMovementsMode = false;
	public Movement[] movements = null;
	public int roll = 0;
	private JMenuItem fileExit;
	private Player playerSelecting;
	int[][] moveables;

	public GUI()  {
		setupFrame();
	}

	public static int getBoardXFromCoord(int xcoord)
	{
		double bx = xcoord - GUI.xOffset;
		bx /= GUI.xSize;
		return (int)bx;
	}

	public static int getBoardYFromCoord(int ycoord)
	{
		double by = ycoord - GUI.yOffset;
		by /= GUI.ySize;
		return (int)by;
	}

	public static int getCoordFromBoardX(int bx)
	{
		double cx = bx * GUI.xSize;
		cx += GUI.xOffset;
		return (int)cx;
	}

	public static int getCoordFromBoardY(int by)
	{
		double cy = by * GUI.ySize;
		cy += GUI.yOffset;
		return (int)cy;
	}

	private ImageIcon makeImageIcon(String filename)
	{		
		ImageIcon icon = new ImageIcon(filename);
		return icon;
	}	

	public void setDice(int first, int second)
	{
		switch(first)
		{
			case 1: 
				diceHolder1.setIcon(dice1);
				break;
			case 2: 
				diceHolder1.setIcon(dice2);
				break;
			case 3: 
				diceHolder1.setIcon(dice3);
				break;
			case 4: 
				diceHolder1.setIcon(dice4);
				break;
			case 5: 
				diceHolder1.setIcon(dice5);
				break;
			case 6: 
			default:
				diceHolder1.setIcon(dice6);
				break;
		}

		switch(second)
		{
			case 1: 
				diceHolder2.setIcon(dice1);
				break;
			case 2: 
				diceHolder2.setIcon(dice2);
				break;
			case 3: 
				diceHolder2.setIcon(dice3);
				break;
			case 4: 
				diceHolder2.setIcon(dice4);
				break;
			case 5: 
				diceHolder2.setIcon(dice5);
				break;
			case 6: 
			default:
				diceHolder2.setIcon(dice6);
				break;
		}
		diceHolder1.repaint();
		diceHolder2.repaint();
	}


	public void setupFrame(){
		frame = new JFrame();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

		jMenu1.setText("File");

		fileExit = new JMenuItem("Exit");
		fileExit.setToolTipText("Exit application");
		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		jMenu1.add(fileExit);
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Game");
		jMenuBar1.add(jMenu2);

		frame.setJMenuBar(jMenuBar1);

		frame.setPreferredSize(new Dimension(516,670));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controls = new JPanel();

		controls.setLayout(new BorderLayout());

		canvas = new GUICanvas(this);
		canvas.addMouseListener(this);
		canvas.setSize(500, 500);
		controls.add(canvas, BorderLayout.NORTH);


		diceControl = new JPanel(new GridBagLayout());
		diceControl.setSize(120, 80);
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		diceControl.add(rollDice, c);

		setDice(1,1);

		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.ipady = 10;
		diceControl.add(diceHolder1, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		c.insets = new Insets(0,10,0,0);
		diceControl.add(diceHolder2, c);
		
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridx = 2;
		c.gridy = 0;
		turnText = new JLabel("It's Player 1's turn.");
		diceControl.add(turnText, c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 2;
		characterText = new JLabel("Go, Kasandra Scarlet!");
		characterText.setFont(characterText.getFont().deriveFont(26.0f));
		diceControl.add(characterText, c);
		
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		instructionsText = new JLabel("Click anywhere in the green area to move there");
		diceControl.add(instructionsText, c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 2;
		c.ipady = 26;
		diceControl.add(cardsButton, c);
		
		c.gridy = 2;
		diceControl.add(checklistButton, c);
		
		controls.add(diceControl, BorderLayout.WEST);
		
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
			return GetNumPlayers();
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
		turnText.setText(String.format("It's Player %d's turn.", playerIndex));
		characterText.setText(String.format("Go, %s!", p.GetCharacter().GetName()));
	}


	@Override
	public void SetPosition(BoardTile bt) {
		// TODO Auto-generated method stub

	}


	@Override
	public void SetRoll(int d1, int d2) {
		//System.out.printf("Setting dice to %d %d\n", d1, d2);
		setDice(d1, d2);
		rollDice.setText(String.format("You rolled: %d!", d1+d2));
	}


	@Override
	public void WaitAction(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}


	@Override
	public Guess GetGuess(Player p) {
		Guess newGuess = new Guess();
		newGuess.room = null;

		return newGuess;
	}


	@Override
	public Guess GetAccusation(Player p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void PresentMovements(Player p, Movement[] m, int[][] moveables, int diceTotal) {
		this.movements = m;
		this.moveables = moveables;
		this.chooseMovementsMode = true;
		this.roll = diceTotal;
		this.playerSelecting = p;

		this.Repaint();
	}


	@Override
	public void NotifyMoved(int nSteps, Room r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		int moveToX = GUI.getBoardXFromCoord(me.getX());
		int moveToY = GUI.getBoardYFromCoord(me.getY());
		System.out.printf("You clicked at: %d, %d\n", GUI.getBoardXFromCoord(me.getX()), GUI.getBoardYFromCoord(me.getY()));
		
		if(moveables[moveToX][moveToY] == 0) return;
		
		if(this.playerSelecting != null)
		{
			this.playerSelecting.CompleteTurn(moveToX,  moveToY, this);
			this.playerSelecting = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Repaint() {
		canvas.repaint();
	}


}
