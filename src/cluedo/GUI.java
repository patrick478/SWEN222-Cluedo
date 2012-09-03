package cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.*;


@SuppressWarnings("serial")
public class GUI extends JFrame implements CluedoUI, MouseListener, WindowListener {

	public static double xOffset = 12;
	public static double yOffset = 12;
	public static double xSize = 16.25;
	public static double ySize = 16.44;
	private Player currentPlayer;
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
	private JMenuItem fileExit;
	private JMenuItem gameShowCards;
	private JMenuItem gameShowNotebook;

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
				int r = JOptionPane.showConfirmDialog(frame, new JLabel(
						"Are you sure you wish to exit?"), "Confirm Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (r == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}

		});

		gameShowCards = new JMenuItem("Show Cards");
		gameShowCards.setToolTipText("Show a list of the cards that you currently hold.");
		gameShowCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showCards(currentPlayer);
				System.out.println("Showing cards");
				}
		});
		gameShowNotebook = new JMenuItem("Show Notebook");

		gameShowNotebook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showNotebook(currentPlayer);
				System.out.println("Showing notebook");
			}
		});
		gameShowNotebook.setToolTipText("Show the notebook, with a record of what cards you have seen so far.");

		jMenu1.add(fileExit);
		jMenu2.add(gameShowCards);
		jMenu2.add(gameShowNotebook);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Game");
		jMenuBar1.add(jMenu2);

		frame.setJMenuBar(jMenuBar1);

		frame.setPreferredSize(new Dimension(516,670));
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		cardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showCards(currentPlayer);
			}

		});
		checklistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showNotebook(currentPlayer);
			}

		});
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

			JOptionPane.showMessageDialog(null,  "The winner is [Player "+args[0]+"]: "+(((Player) args[1]).GetCharacter().GetName()));
			break;

		case Loser:
			JOptionPane.showMessageDialog(null,  "The loser is [Player "+args[0]+"]: "+(((Player) args[1]).GetCharacter().GetName()));
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
		currentPlayer = p;
	}


	@Override
	public void SetPosition(BoardTile bt) {
		// this isn't need

	}


	@Override
	public void SetRoll(int d1, int d2) {
		setDice(d1, d2);
		rollDice.setText(String.format("You rolled: %d!", d1+d2));
	}


	@Override
	public void WaitAction(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}


	@Override
	public Guess GetGuess(Player p) {
		String[] charactersToShow = new String[Character.characters.length];
		int i = 0;
		for(Character c :  Character.characters){
			charactersToShow[i] = c.GetName();
			i++;

		}
		String[] weaponsToShow = new String[Weapon.weapons.length];
		i = 0;
		for(Weapon w :  Weapon.weapons){
			weaponsToShow[i] = w.GetName();
			i++;
		}


		Room room = null;
		String character = (String) JOptionPane.showInputDialog(null, "Please select a character you wish to guess is the murderer", "GUESS: Murderer Selection", JOptionPane.PLAIN_MESSAGE, null,charactersToShow, charactersToShow[0]);
		String weapon = (String)JOptionPane.showInputDialog(null, "Please select a weapon you wish to guess is the murder weapon", "GUESS: Weapon Selection", JOptionPane.PLAIN_MESSAGE, null, weaponsToShow, weaponsToShow[0]);
		
		Character murderer = null;
		Weapon murderWeap = null;
		for(Character c :  Character.characters)
			if(character.equals(c.GetName()))
				murderer = c;
				
		for(Weapon w :  Weapon.weapons)
			if(weapon.equals(w.GetName()))
				murderWeap = w;	
		

		System.out.println(murderer.GetName());
		System.out.println(murderWeap.GetName());
		
		Guess newGuess = new Guess(room, murderer, murderWeap);
		//System.out.println(newGuess.toString());
		return newGuess;
	}


	@Override
	public Guess GetAccusation(Player p) {
		Guess newGuess = this.GetGuess(p);
		newGuess.isAccusation = true;
		
		String[] roomsToShow = new String[Room.rooms.size()];
		Room[] roomList = (Room[]) Room.rooms.values().toArray(new Room[Room.rooms.size()]);
		
		int i = 0;
		for(Room r :  roomList){
			roomsToShow[i] = r.GetName();
			i++;

		}
		String room = (String)JOptionPane.showInputDialog(null, "Please select a room you wish to guess is the murder scene", "GUESS: Room Selection", JOptionPane.PLAIN_MESSAGE, null, roomsToShow, roomsToShow[0]);		
		Room murderRoom = null;
		for(Room r :  roomList)
			if(room.equals(r.GetName()))
				murderRoom = r;
		
		newGuess.room = murderRoom;
		
		return newGuess;
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
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void Repaint() {
		canvas.repaint();
	}

	public void windowClosing(WindowEvent e) {
		// Ask the user to confirm they wanted to do this
		int r = JOptionPane.showConfirmDialog(this, new JLabel(
				"Are you sure you wish to exit?"), "Confirm Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * This method is called after the X button has been depressed.
	 * @param e
	 */
	public void windowClosed(WindowEvent e) {}

	// The following methods are part of the WindowListener interface,
	// but are not needed here.
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}


	public void showCards(Player p){
		final JFrame cardFrame = new JFrame("Cards being held by player:" + p.GetCharacter());
		cardFrame.setLayout(new GridLayout(0,1));
		ArrayList<GameObject> cards =  p.getCards();
		for(GameObject go: cards){
			cardFrame.add(new JLabel(go.GetName()));
		}
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				cardFrame.dispose();
			}
		});
		
		cardFrame.add(close);
		cardFrame.pack();
		cardFrame.setLocationRelativeTo(frame);
		cardFrame.setVisible(true);
	}

	public void showNotebook(Player p){

		final JFrame checklistFrame = new JFrame("Cards being held by player:" + p.GetCharacter());
		checklistFrame.setLayout(new GridLayout(0,1));
		ArrayList<GameObject> cards =  p.getChecklist();
		for(GameObject go: cards){
			checklistFrame.add(new JLabel(go.GetName()));
		}
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				checklistFrame.dispose();
			}
		});

		checklistFrame.add(close);
		checklistFrame.pack();
		checklistFrame.setLocationRelativeTo(frame);
		checklistFrame.setVisible(true);
	}

}
