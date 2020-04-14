package package1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Controller {
	private GUI theGUI;	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	public Controller(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

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
		
		JTextField courseName = new JTextField();
		JTextField courseNum = new JTextField();
		
		Object[] field1 = {
				"Course Name:", courseName,
				"Course Number:", courseNum,
		};
		
    	int option = JOptionPane.showConfirmDialog(null,field1, "Search Course Catalogue", JOptionPane.CANCEL_OPTION);
    	
    	socketOut.println(option);
    	
    	if(option == 0) {
    		socketOut.println(courseName.getText());
    		socketOut.println(courseNum.getText());
    		
    		theGUI.remove(theGUI.display);

    		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    		theGUI.display.setSize(1080,720);
    		theGUI.add("Center",theGUI.display);
            theGUI.add(theGUI.display);
            theGUI.pack();
    	}
	}
	
	public void addRegPressed() {
		socketOut.println("2");
	}
	
	public void removeRegPressed() {
		socketOut.println("3");
	}
	
	public void displayCataPressed() {
		socketOut.println("4");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
	public void displayTakenPressed() {
		socketOut.println("5");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
	public void displayRegPressed() {
		socketOut.println("6");
		theGUI.remove(theGUI.display);

		theGUI.display = new JScrollPane(updateTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		theGUI.display.setSize(1080,720);
		theGUI.add("Center",theGUI.display);
        theGUI.add(theGUI.display);
        theGUI.pack();
	}
	
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
