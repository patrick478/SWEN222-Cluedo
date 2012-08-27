package cluedo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class GUI {


    private static JFrame frame;
    private static BufferedImage boardImage;
    private static JComponent board;
	private static JPanel controls; 

	
	private static JButton rollDice = new JButton("Roll Dice");
	
	

    public static void main(String[] args){
	setupFrame();

    }


    /** Creates a frame with a JComponent in it.
     *  Clicking in the frame will close it. */
    public static void setupFrame(){
	frame = new JFrame();
	try {
		boardImage = ImageIO.read(new File("board.jpg"));
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
	
	controls = new JPanel(){
			protected void paintComponent(Graphics g){
				
			controls.add(rollDice, BorderLayout.WEST);	
			    }
	};    
	    
	JSplitPane splitPane = new JSplitPane(0,true, board, controls);
	splitPane.setDividerLocation(715);
	
	frame.add(splitPane, BorderLayout.CENTER);
	
	
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }	
    


    





}
