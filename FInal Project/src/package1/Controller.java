package package1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controller {
	private GUI theGUI;	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	public Controller(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	//Default controller to have eclipse stop yelling at me 
	public void startGUI() {
		theGUI = new GUI(this);
		theGUI.setVisible(true);
		getStudentInfo();
	}
	
	public void getStudentInfo() {
		
		// Take Student Name and Id using a dialog pane
        String nameIn = "";
        String idIn = "";
        String panelTitle = "Enter Student Details";
        
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
    		
    		if(option == JOptionPane.CANCEL_OPTION)
        		System.exit(0);
        	
            nameIn = courseName.getText();
            idIn = courseNum.getText();
            
            if (nameIn.matches("^[a-zA-Z]*$") && idIn.matches("^[0-9]*$") && !nameIn.equals("") && !idIn.equals("")) {
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
	
	public void searchCataPressed() {
		socketOut.println("1");
	}
	
	public void removeRegPressed() {
		socketOut.println("2");

	}
	
	public void displayCataPressed() {
		socketOut.println("3");

	}
	
	public void displayTakenPressed() {
		socketOut.println("4");

	}
	
	public void displayRegPressed() {
		socketOut.println("5");

	}
}