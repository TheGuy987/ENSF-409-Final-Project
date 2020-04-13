package package1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class extending JFrame that handles the main GUI elements of the program. It contains various instance
 * variables relating to the title, label, panels, ect. of the frame, as well as a Controller object that
 * links the class to the functions of a BinSearchTree object.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */

public class GUI extends JFrame {
	JButton searchCata, removeReg, displayCata, displayTaken, displayReg;
	JLabel title;
	JPanel titlePanel, buttonPanel;
	JScrollPane display;
	Controller control;
	
	
	/**
	 * Constructor that takes no input and creates a frame representing the menu screen
	 * of the Student Record application. It uses lambda expressions to monitor button
	 * presses, which interact with variable control.
	 */
	public GUI(Controller control) {
		this.control = control;
		
		titlePanel = new JPanel();
		title = new JLabel("Course Registeration App");
		title.setForeground(Color.white);
		titlePanel.add(title);
		
		searchCata = new JButton("Search Course Catalogue");
		searchCata.setForeground(Color.white);
		searchCata.setBackground(Color.gray);
		searchCata.setFocusPainted(false);
		
		removeReg = new JButton("Remove Registeration");
		removeReg.setForeground(Color.white);
		removeReg.setBackground(Color.gray);
		removeReg.setFocusPainted(false);
		
		displayCata = new JButton("Display Course Catalogue");
		displayCata.setForeground(Color.white);
		displayCata.setBackground(Color.gray);
		displayCata.setFocusPainted(false);
		
		displayTaken = new JButton("Display Courses Taken");
		displayTaken.setForeground(Color.white);
		displayTaken.setBackground(Color.gray);
		displayTaken.setFocusPainted(false);
		
		displayReg = new JButton("Display Registered Courses");
		displayReg.setForeground(Color.white);
		displayReg.setBackground(Color.gray);
		displayReg.setFocusPainted(false);
		
		
		searchCata.addActionListener((ActionEvent e) -> { 
			control.searchCataPressed();
			});
		
		removeReg.addActionListener((ActionEvent e) -> { 
			control.removeRegPressed();
			});
		
		displayCata.addActionListener((ActionEvent e) -> { 
			control.displayCataPressed();
			});
		
		displayTaken.addActionListener((ActionEvent e) -> { 
			control.displayTakenPressed();
			});
		
		displayReg.addActionListener((ActionEvent e) -> { 
			control.displayRegPressed();
			});
		
		
		buttonPanel = new JPanel();
		buttonPanel.add(searchCata);
		buttonPanel.add(removeReg);
		buttonPanel.add(displayCata);
		buttonPanel.add(displayTaken);
		buttonPanel.add(displayReg);
		
		JTextArea ta = new JTextArea("",23,62);
		ta.setForeground(Color.white);
		ta.setBackground(Color.DARK_GRAY);
		
		display = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		display.setBackground(Color.DARK_GRAY);
		
		this.add("South",buttonPanel);
		this.add("North",titlePanel);
		this.add("Center",display);
		this.setSize(1080,650);
		this.setLocationRelativeTo(null);
		
		titlePanel.setBackground(Color.DARK_GRAY);
		buttonPanel.setBackground(Color.DARK_GRAY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Main method of the program. It creates a new insance of GUI, which calls
	 * its constructor and starts the program.
	 * @param args String array that can be read form the command line.
	 */
}