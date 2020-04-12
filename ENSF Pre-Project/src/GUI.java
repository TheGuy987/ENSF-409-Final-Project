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
	JButton searchCata, removeReg, displayCata, displayCoursesTaken, displayReg;
	JLabel title;
	JPanel titlePanel, buttonPanel;
	JScrollPane display;
	Controller control;
	
	/**
	 * Constructor that takes no input and creates a frame representing the menu screen
	 * of the Student Record application. It uses lambda expressions to monitor button
	 * presses, which interact with variable control.
	 */
	public GUI() {
		control = new Controller(this);
		
		titlePanel = new JPanel();
		title = new JLabel("An Application to Maintain Student Records");
		titlePanel.add(title);
		
		insert = new JButton("Insert");
		find = new JButton("Find");
		browse = new JButton("Browse");
		createTree = new JButton("Create Tree From File");
		
		insert.addActionListener((ActionEvent e) -> { 
			control.insertPressed();
			});
		
		find.addActionListener((ActionEvent e) -> { 
			control.findPressed();
			});
		
		browse.addActionListener((ActionEvent e) -> { 
			control.browsePressed();
			});
		
		createTree.addActionListener((ActionEvent e) -> { 
			control.createTreePressed();
			});
		
		buttonPanel = new JPanel();
		buttonPanel.add(insert);
		buttonPanel.add(find);
		buttonPanel.add(browse);
		buttonPanel.add(createTree);
		
		display = new JScrollPane();
		
		this.add("South",buttonPanel);
		this.add("North",titlePanel);
		this.add("Center",display);
		this.setSize(720,480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Main method of the program. It creates a new insance of GUI, which calls
	 * its constructor and starts the program.
	 * @param args String array that can be read form the command line.
	 */
	public static void main(String[] args) {
		GUI app = new GUI();
		app.setVisible(true);
	}
}