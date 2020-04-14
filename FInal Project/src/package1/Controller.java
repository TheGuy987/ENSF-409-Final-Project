package package1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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
	/**
	 * The method getStudentInfo creates a popup that asks for the student's name, student's id and the courses
	 * they have already taken. They can choose to add additional courses by pressing "Yes" after a prompt or "No"
	 * to continue to the menu
	 */
	public void getStudentInfo() {
		// Take Student Name and Id using a dialog pane
        String nameIn = "";
        String idIn = "";
        String panelTitle = "Enter Student Details";
        boolean duplicate;
        
        
        do {
    		JTextField studentName = new JTextField();
    		JTextField studentId = new JTextField();
    		
    		Object[] field1 = {
    				"Student Name:", studentName,
    				"Student id:", studentId,
    		};
    		
        	int option = JOptionPane.showConfirmDialog(null,field1, panelTitle, JOptionPane.CANCEL_OPTION);
        	
        	if(option == JOptionPane.CANCEL_OPTION)
        		System.exit(0);
        	
            nameIn = studentName.getText();
            idIn = studentId.getText();
            
            if (nameIn.matches("^[a-zA-Z]*$") && idIn.matches("^[0-9]*$") && !nameIn.equals("") && !idIn.equals("")) {
        		socketOut.println(nameIn);
        		socketOut.println(idIn);
                break;
            } else {
            	panelTitle = "Error! Re-enter Student Details";
            }
        } while (!nameIn.matches("^[a-zA-Z]*$") || !idIn.matches("^[0-9]*$") || nameIn.equals("") || idIn.equals(""));
		
        // Ask students what courses they have taken using a dialog pane
        int check = 0;
        nameIn = "";
        idIn = "";
        panelTitle = "Courses Taken";
        do {
    		JTextField courseName = new JTextField();
    		JTextField courseNum = new JTextField();
    		
    		Object[] field2 = {
    				"Course Name:", courseName,
    				"Course Number: ", courseNum,
    		};
    		
    		int option = JOptionPane.showConfirmDialog(null,field2, panelTitle, JOptionPane.CANCEL_OPTION);
    		
    		//sends option to server so it knows whether the user pressed "ok" or "cancel"
        	socketOut.println(option);

    		if(option == JOptionPane.CANCEL_OPTION)
        		break;
        	
            nameIn = courseName.getText();
            idIn = courseNum.getText();
            ArrayList<String> alreadyEntered = new ArrayList<String>();
            duplicate = false;
            for(int i = 0; i < alreadyEntered.size(); i++) {
            	if(alreadyEntered.get(i).matches(nameIn + idIn)) {
            		duplicate = true;
            	}
            }
            
            if(duplicate) {
            	panelTitle = "Error! You already entered that! Re-enter Courses Taken";
            }
            
            else if (nameIn.matches("^[a-zA-Z]*$") && idIn.matches("^[0-9]*$") && !nameIn.equals("") && !idIn.equals("")) {
            	alreadyEntered.add(nameIn + idIn);
            	for(int i = 0; i < alreadyEntered.size(); i++) {
            		System.out.println(alreadyEntered.get(i));
            	}
        		socketOut.println(nameIn);
        		socketOut.println(idIn);
        		panelTitle = "Courses Taken";
        		check =JOptionPane.showConfirmDialog(null, "Do you want to add another course?", panelTitle, JOptionPane.OK_OPTION);
    			socketOut.println(check);   			
            } else {
            	panelTitle = "Error! Re-enter Courses Taken";
            }
        }while(check == 0 || !nameIn.matches("^[a-zA-Z]*$") || !idIn.matches("^[0-9]*$") || nameIn.equals("") || idIn.equals(""));
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
    		
    		try {
				while(!socketIn.ready());
	    		updateScrollPanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	/**
	 * Method updateScrollPanel removes the previous scroll panel, calls updateTextArea and then displays a new
	 * scroll panel with the updated text
	 */
	public void updateScrollPanel() {
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
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
		
		JTextArea jta = new JTextArea(display, 40, 87);
		jta.setForeground(Color.white);
		jta.setBackground(Color.DARK_GRAY);
		return jta;
	}
	
}