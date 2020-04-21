package package1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.io.IOException;

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
	JButton searchCata, addReg, removeReg, displayCata, displayTaken, displayReg, logout, createCourse;
	JLabel title;
	JPanel titlePanel, buttonPanel, logoutPanel;
	JScrollPane display;
	Controller control;
	
	/**
	 * Constructor that takes no input and creates a frame representing the menu screen
	 * of the Student Record application. It uses lambda expressions to monitor button
	 * presses, which interact with variable control. it is able to accomidate both a 
	 * student and an admin user.
	 */
	public GUI(Controller control) {
		this.control = control;		
		buttonPanel = new JPanel();
		
		if(control.getUser() != 0) {	
			
			// Admin Options
			if(control.getUser() == 1) {
				createCourse = new JButton("Create Course");
				createCourse.setForeground(Color.white);
				createCourse.setBackground(Color.gray);
				createCourse.setFocusPainted(false);
				createCourse.setVisible(true);
				
				createCourse.addActionListener((ActionEvent e) -> { 
					control.createCoursePressed();
					});
					
				buttonPanel.add(createCourse);
				buttonPanel.revalidate();
			}
			
			// Student Options
			if(control.getUser() == 2) {
				addReg = new JButton("Register for a Course");
				addReg.setForeground(Color.white);
				addReg.setBackground(Color.gray);
				addReg.setFocusPainted(false);
				addReg.setVisible(true);
				
				removeReg = new JButton("Remove Registeration");
				removeReg.setForeground(Color.white);
				removeReg.setBackground(Color.gray);
				removeReg.setFocusPainted(false);
				removeReg.setVisible(true);
				
				displayTaken = new JButton("Display Courses Taken");
				displayTaken.setForeground(Color.white);
				displayTaken.setBackground(Color.gray);
				displayTaken.setFocusPainted(false);
				displayTaken.setVisible(true);
				
				displayReg = new JButton("Display Registered Courses");
				displayReg.setForeground(Color.white);
				displayReg.setBackground(Color.gray);
				displayReg.setFocusPainted(false);
				displayReg.setVisible(true);
				
				addReg.addActionListener((ActionEvent e) -> { 
					control.addRegPressed();
					});
				
				removeReg.addActionListener((ActionEvent e) -> { 
					control.removeRegPressed();
					});
				
				displayTaken.addActionListener((ActionEvent e) -> { 
					control.displayTakenPressed();
					});
				
				displayReg.addActionListener((ActionEvent e) -> { 
						control.displayRegPressed();
					});
				
				buttonPanel.add(addReg);
				buttonPanel.revalidate();
				buttonPanel.add(removeReg);
				buttonPanel.revalidate();
				buttonPanel.add(displayTaken);
				buttonPanel.revalidate();
				buttonPanel.add(displayReg);
				buttonPanel.revalidate();
			}
			
			searchCata = new JButton("Search Course Catalogue");
			searchCata.setForeground(Color.white);
			searchCata.setBackground(Color.gray);
			searchCata.setFocusPainted(false);
			searchCata.setVisible(true);
			
			displayCata = new JButton("Display Course Catalogue");
			displayCata.setForeground(Color.white);
			displayCata.setBackground(Color.gray);
			displayCata.setFocusPainted(false);	
			displayCata.setVisible(true);
			
			searchCata.addActionListener((ActionEvent e) -> { 
				control.searchCataPressed();
				});			
			
			displayCata.addActionListener((ActionEvent e) -> { 
				control.displayCataPressed();
				});
			
			buttonPanel.add(searchCata);
			buttonPanel.revalidate();
			buttonPanel.add(displayCata);
			buttonPanel.revalidate();
		}
		
		logout = new JButton("Logout");
		logout.setForeground(Color.white);
		logout.setBackground(Color.gray);
		logout.setFocusPainted(false);
		logout.setVisible(true);
		
		logout.addActionListener((ActionEvent e) -> { 
			control.logoutPressed();
			return;
		});
		
		titlePanel = new JPanel();
		title = new JLabel("Course Registeration App");
		title.setForeground(Color.white);
		title.setBackground(Color.gray);
		titlePanel.add(title);
		titlePanel.revalidate();
		logout.setBounds(975, 3, 75, 20);
		this.add(logout);		
		display = new JScrollPane();
		
		JTextArea ta = new JTextArea("",35,62);
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
}