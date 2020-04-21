package src.package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * The Controller class handles all of the client side logic and also the actions that happen when a button is pressed
 * @author Thomas (Hong Pan)
 *
 */
public class Controller {
	private GUI theGUI;	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private int user = 0;
	
	/**
	 * The constructor for controller constructs the controller class and passes a BufferedReader and a Printwriter
	 * to it. 
	 * @param socketIn
	 * @param socketOut
	 */
	public Controller(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	/**
	 * The method startGUI constructs a GUI and makes it visible. Then a popup appears to ask the user for
	 * their name, student ID and courses taken
	 */
	public void startGUI() {
		theGUI = new GUI(this);
		theGUI.setVisible(true);
		getStudentInfo();
	}
	
	public void getAdminInfo() {
		// Take Student ID and password using a dialog pane
        String iDIn = "";
        String passwordIn = "";
        String panelTitle = "Enter Login Information";
        boolean duplicate;
        String foundUser = "0";
        socketOut.flush();
        socketOut.println("1");
        
        do {
    		JTextField adminID = new JTextField();
    		JTextField adminPassword = new JTextField();
    		
    		Object[] field1 = {
    				"Admin ID:", adminID,
    				"Admin password:", adminPassword,
    		};
    		
    		Object[] options = { "Login", "Cancel", "Student Login" };
        	int option = JOptionPane.showOptionDialog(null,field1, panelTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        	
        	// Show student login
        	if(option == JOptionPane.CANCEL_OPTION) {
        		socketOut.println("-1");
        		getStudentInfo();
        		return;
        	}
        	
        	// Cancel/Close pressed
        	if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION) {
        		socketOut.println("0");
        		System.exit(0);
        	}
        	
            iDIn = adminID.getText();
            passwordIn = adminPassword.getText();
            
            if (iDIn.matches("^[0-9]*$") && !passwordIn.equals("") && !iDIn.equals("")) {
        		socketOut.println(iDIn);
        		socketOut.println(passwordIn);
        		try {
        			foundUser = socketIn.readLine();
					if(foundUser.equals("1")) {
						setUser(1);
						theGUI.setVisible(false);
						theGUI.dispose();
						theGUI = new GUI(this);
						theGUI.setVisible(true);
						// call the admin here
						break;
					}
					else
						panelTitle = "Error! Re-enter Admin Details";
						
				} catch (IOException e) {
					panelTitle = "Error! Re-enter Admin Details";
				}
            } else {
            	panelTitle = "Error! Re-enter Admin Details";
            }
        } while (!iDIn.matches("^[0-9]*$") || passwordIn.equals("") || iDIn.equals("") || !foundUser.equals("1"));
	}
	
	/**
	 * The method getStudentInfo creates a popup that asks for the student's name, student's id and the courses
	 * they have already taken. They can choose to add additional courses by pressing "Yes" after a prompt or "No"
	 * to continue to the menu
	 */
	public void getStudentInfo() {
		// Take Student ID and password using a dialog pane
        String iDIn = "";
        String passwordIn = "";
        String panelTitle = "Enter Login Information";
        boolean duplicate;
        String foundUser = "0";
        socketOut.flush();
        socketOut.println("0");
        
        
        do {
    		JTextField studentId = new JTextField();
    		JTextField studentPassword = new JTextField();
    		
    		Object[] field1 = {
    				"Student ID:", studentId,
    				"Student password:", studentPassword,
    		};
    		
    		Object[] options = { "Login", "Cancel", "Admin Login" };
        	int option = JOptionPane.showOptionDialog(null,field1, panelTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        	
        	// Show admin login
        	if(option == JOptionPane.CANCEL_OPTION) {
        		socketOut.println("-1");
        		getAdminInfo();
        		return;
        	}
        	
        	if(option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION) {
        		socketOut.println("0");
        		System.exit(0);
        	}
        	
            iDIn = studentId.getText();
            passwordIn = studentPassword.getText();
            
            if (iDIn.matches("^[0-9]*$") && !passwordIn.equals("") && !iDIn.equals("")) {
        		socketOut.println(iDIn);
        		socketOut.println(passwordIn);
        		try {
        			foundUser = socketIn.readLine();
					if(foundUser.equals("1")) {						
						setUser(2);
						theGUI.setVisible(false);
						theGUI.dispose();
						theGUI = new GUI(this);
						theGUI.setVisible(true);
						break;
					}
					else
						panelTitle = "Error! Re-enter Student Details";
						
				} catch (IOException e) {
					panelTitle = "Error! Re-enter Student Details";
				}
            } else {
            	panelTitle = "Error! Re-enter Student Details";
            }
        } while (!iDIn.matches("^[0-9]*$") || passwordIn.equals("") || iDIn.equals("") || !foundUser.equals("1"));
	}
	
	
	/**
	 * the method searchCataPressed is used to search for courses in the Course Catalogue. A popup will appear
	 * asking for the course name and number. Then a message is displayed on the menu based on whether or not 
	 * the course was found. The user can press the cancel button in order to cancel the operation 
	 */
	public void searchCataPressed() {
		socketOut.println("1");
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		
		Object[] field1 = {
				"Course Name:", courseName,
				"Course Number:", courseNum,
		};
		
    	int option = JOptionPane.showConfirmDialog(null,field1, "Search Course Catalogue", JOptionPane.CANCEL_OPTION);
    	
    	socketOut.println(option);
    	
    	if(option == 0) {
    		socketOut.println(courseName.getText().toUpperCase());
    		socketOut.println(courseNum.getText());
    		
    		updateScrollPanel();
    	}
	}
	/**
	 * The method addRegPressed is used by the user to register for a course. A popup will appear asking for the
	 * course name and course number. Then a message is displayed on the menu based on whether or not the student
	 * was able to successfully register for the course
	 */
	public void addRegPressed() {
		socketOut.println("2");
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		
		Object[] field1 = {
				"Course Name:", courseName,
				"Course Number:", courseNum,
		};
		
    	int option = JOptionPane.showConfirmDialog(null,field1, "Register for a Course", JOptionPane.CANCEL_OPTION);
    	
    	//sends option to server so it knows whether the user pressed "ok" or "cancel"
    	socketOut.println(option);
    	
    	if(option == 0) {
    		socketOut.println(courseName.getText().toUpperCase());
    		socketOut.println(courseNum.getText());
    		
    		try {
				while(!socketIn.ready());
	    		updateScrollPanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	
	public void removeRegPressed() {
		socketOut.println("3");
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		
		Object[] field1 = {
				"Course Name:", courseName,
				"Course Number:", courseNum,
		};
		
    	int option = JOptionPane.showConfirmDialog(null,field1, "Remove a Course", JOptionPane.CANCEL_OPTION);
    	
    	//sends option to server so it knows whether the user pressed "ok" or "cancel"
    	socketOut.println(option);
    	
    	if(option == 0) {
    		socketOut.println(courseName.getText());
    		socketOut.println(courseNum.getText());
    		
    		updateScrollPanel();
    	}
	}
	/**
	 * The method displayCataPressed is used to display the course catalog on the menu
	 */
	public void displayCataPressed() {
		socketOut.println("4");
		updateScrollPanel();
	}
	/**
	 * The method displayTakenPressed is used to display all the courses the student has already taken
	 */
	public void displayTakenPressed() {
		socketOut.println("5");
		updateScrollPanel();
	}
	/**
	 * The method displayRegPress is used to display all the courses the student has registered for 
	 */
	public void displayRegPressed() {
		socketOut.println("6");
		updateScrollPanel();
	}
	
	public void logoutPressed() {
		socketOut.println("7");
		theGUI.dispose();
		startGUI();
	}
	
	public void createCoursePressed() {
		socketOut.println("8");
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		JTextField courseSec = new JTextField();
		
		Object[] field1 = {
				"Course Name:", courseName,
				"Course Number:", courseNum,
				"Sections:",courseSec,
		};
		
		int option;
		String title = "Create New Course Wizard";
		do {
			option = JOptionPane.showConfirmDialog(null,field1, title, JOptionPane.CANCEL_OPTION);
			
			System.out.println("Option is: "+option);
	    	socketOut.println(option); //#1
	    	
	    	if(option==JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION){
				return;
	    	}
	    	
			if(courseName.getText().equals("") || courseNum.getText().equals("") || courseSec.getText().contentEquals("") || !courseName.getText().matches("^[a-zA-Z]*$") || !courseNum.getText().matches("^[0-9]*$") || !courseSec.getText().matches("^[0-9]*$"))
				title = "Error: Unacceptable Input, Try Again";
			else
				break;
		}while(true);
    	

    	
    	if(option == 0) {
    		socketOut.println(courseName.getText().toUpperCase());
    		socketOut.println(courseNum.getText());
    		socketOut.println(courseSec.getText());
    		
    		for(int x=0; x<Integer.parseInt(courseSec.getText()); x++){
    			Object[] field2 = {
    				"Section Capacity:", courseNum,
    			};
    			courseNum.setText("100");
    			title = "Enter Section Number "+(x+1)+" Capacity";
    			do {
    				option = JOptionPane.showConfirmDialog(null,field2, title, JOptionPane.CANCEL_OPTION);
    				if(option==JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION){
    					socketOut.println("0");
    					return;
    				}else if(courseNum.getText().equals("") || !courseNum.getText().matches("^[0-9]*$")) {
    					title = "Error: Unacceptable Input, Try Again";
    				}else {
    					break;
    				}
    			}while(true);
				
    			socketOut.println("1");
				socketOut.println(courseNum.getText());
				
    		}
    		
    		try {
				while(!socketIn.ready());
	    		updateScrollPanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		// ask for additional course options required
    	}
		updateScrollPanel();
	}
	
	/**
	 * Method updateScrollPanel removes the previous scroll panel, calls updateTextArea and then displays a new
	 * scroll panel with the updated text
	 */
	public void updateScrollPanel() {
		theGUI.remove(theGUI.display);
		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,650);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	/**
	 * Method updateTextArea creates a new JTextArea based on a string input from the server
	 * @return a new JTextArea with text from the server
	 */
	public JTextArea updateTextArea() {
		String display="";
		try {
			while(socketIn.ready()) {
				display+=socketIn.readLine();
				display+="\n";
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JTextArea jta = new JTextArea(display, 35, 65);
		jta.setForeground(Color.white);
		jta.setBackground(Color.DARK_GRAY);
		return jta;
	}
	
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	
}