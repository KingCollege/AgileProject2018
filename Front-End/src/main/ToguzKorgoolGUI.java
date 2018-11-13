package main;
import java.awt.*;

import javax.swing.*;

public class ToguzKorgoolGUI extends JFrame{
	private static int MAX_ROCKS = 9;
	private static int MAX_HOLES = 9;
	
	private JButton[] playerHoles;
	private JButton[] computerHoles;
	private JButton pKazna;
	private JButton cKazna;
	private JLabel pTimer;
	private JLabel cTimer;
	
	
	public ToguzKorgoolGUI() {
		super("Toguz Korgool");
		//Initialisation
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerHoles = new JButton[9];
		computerHoles = new JButton[9];
		pKazna = new JButton("Player Kazna");
		cKazna = new JButton("Computer Kazna");
		pTimer = new JLabel("Player Time: ");
		cTimer = new JLabel("Computer Time: ");
		
		
		//Set up GUI
		JPanel center = new JPanel(new GridLayout(2,1));
		center.add(cKazna);
		center.add(pKazna);
		add(center, BorderLayout.CENTER);
		setUpHoles();
		setUpTimers();
		pack();
		setVisible(true);
	}
	
	private void setUpTimers() {
		GridLayout gl = new GridLayout(2, 1);
		JPanel west = new JPanel(gl);
		west.add(cTimer);
		west.add(pTimer);
		//start timer...
		add(west, BorderLayout.WEST);
	}
	
	private void setUpHoles() {
		GridLayout gl = new GridLayout(1,9);
		JPanel south = new JPanel(gl);
		JPanel north = new JPanel(gl);
		//player
		for(int i =0; i < MAX_HOLES; i++) {
			playerHoles[i] = new JButton("PHole 1: " + MAX_ROCKS);
			south.add(playerHoles[i]);
		}
		add(south, BorderLayout.SOUTH);
		//computer
		for(int i =0; i < MAX_HOLES; i++) {
			computerHoles[i] = new JButton("CHole 1: " + MAX_ROCKS);
			computerHoles[i].setEnabled(false);
			north.add(computerHoles[i]);
		}
		add(north, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		new ToguzKorgoolGUI();
	}
}
