package cluedo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class GUI extends JFrame {


	private static JFrame frame;
	private static BufferedImage boardImage;	
	private static JComponent board;
	private static JPanel controls; 
	private static JPanel diceControl; 
	private static JLabel diceHolder1 = new JLabel();
	private static JLabel diceHolder2= new JLabel();
	private static JMenu jMenu1;
	private static JMenu jMenu2;
	private static JMenuBar jMenuBar1;

	

	private static ImageIcon dice1 = makeImageIcon("src/resources/dice1.jpg");
	private static ImageIcon dice2 = makeImageIcon("src/resources/dice2.jpg");
	private static ImageIcon dice3 = makeImageIcon("src/resources/dice3.jpg");
	private static ImageIcon dice4 = makeImageIcon("src/resources/dice4.jpg");
	private static ImageIcon dice5 = makeImageIcon("src/resources/dice5.jpg");
	private static ImageIcon dice6 = makeImageIcon("src/resources/dice6.jpg");

	private static JButton rollDice = new JButton("Roll Dice");



	public GUI()  {
		setupFrame();
	}


	private static ImageIcon makeImageIcon(String filename) {		
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = GUI.class.getResource(filename);

		ImageIcon icon = null;
		if (imageURL != null) {
			icon = new ImageIcon(imageURL);
		}
		return icon;
	}


	public static void main(String[] args){
		setupFrame();
	}
	
	
	public static void setDice(int first, int second){
		switch(first){
			case 1: diceHolder1.setIcon(dice1);
			case 2: diceHolder1.setIcon(dice2);
			case 3: diceHolder1.setIcon(dice3);
			case 4: diceHolder1.setIcon(dice4);
			case 5: diceHolder1.setIcon(dice5);;
			default: diceHolder1.setIcon(dice6);
			
		}
		switch(second){
		case 1: diceHolder2.setIcon(dice1);
		case 2: diceHolder2.setIcon(dice2);
		case 3: diceHolder2.setIcon(dice3);
		case 4: diceHolder2.setIcon(dice4);
		case 5: diceHolder2.setIcon(dice5);;
		default: diceHolder2.setIcon(dice6);

		
	}
	}


	public static void setupFrame(){
		frame = new JFrame();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Game");
		jMenuBar1.add(jMenu2);

		frame.setJMenuBar(jMenuBar1);


		try {
			boardImage = ImageIO.read(new File("src/resources/board.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		frame.setPreferredSize(new Dimension(715,950));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		board = new JComponent(){
			protected void paintComponent(Graphics g){
				g.drawImage(boardImage, 0, 0,frame.getWidth(),frame.getWidth(), null);}
		};	


		controls = new JPanel();
		controls.setLayout(new BorderLayout());

		
		
		diceControl = new JPanel();
		diceControl.setLayout(new BorderLayout());
		diceControl.add(rollDice, BorderLayout.NORTH);
		
		setDice(1,1);
		
		diceControl.add(diceHolder1,BorderLayout.SOUTH);
		diceControl.add(diceHolder2, BorderLayout.SOUTH);



		controls.add(diceControl,BorderLayout.WEST);


		JSplitPane splitPane = new JSplitPane(0,true, board, controls);
		splitPane.setDividerLocation(715);

		frame.add(splitPane, BorderLayout.CENTER);


		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}	









}
