package package1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		titlePanel.add(title);
		
		searchCata = new JButton("Search Course Catalogue");
		removeReg = new JButton("Remove Registeration");
		displayCata = new JButton("Display Course Catalogue");
		displayTaken = new JButton("Display Courses Taken");
		displayReg = new JButton("Display Registered Courses");
		
		/*
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
		*/
		
		buttonPanel = new JPanel();
		buttonPanel.add(searchCata);
		buttonPanel.add(removeReg);
		buttonPanel.add(displayCata);
		buttonPanel.add(displayTaken);
		buttonPanel.add(displayReg);
		
		display = new JScrollPane();
		
		this.add("South",buttonPanel);
		this.add("North",titlePanel);
		this.add("Center",display);
		this.setSize(1080,720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void getStudentInfo() {
		
	}

}